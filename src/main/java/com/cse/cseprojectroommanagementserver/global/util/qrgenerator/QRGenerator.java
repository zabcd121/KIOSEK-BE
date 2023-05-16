package com.cse.cseprojectroommanagementserver.global.util.qrgenerator;

import com.cse.cseprojectroommanagementserver.global.dto.QRImage;
import com.cse.cseprojectroommanagementserver.global.error.ErrorCode;
import com.cse.cseprojectroommanagementserver.global.error.exception.FileSystemException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class QRGenerator {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final int DARK_COLOR = 0xFF000000; //rgba 투명도 조절해야 검은색이 채워짐
    private static final int LIGHT_COLOR = 0xFFFFFFFF;
    private static final String EXTENSION = ".png";

    @Value("${fileDir.inbound}")
    private String inbound;

    @Value("${fileDir.outbound}")
    private String outbound;

    @Value("${fileDir.accounts}")
    private String accountQRDir;

    @Value("${fileDir.reservations}")
    private String reservationQRDir;

    public QRImage createAccountQRCodeImage() throws FileSystemException {
        try{
            return createQRCodeImage(reservationQRDir);
        } catch(QRNotCreatedException e) {
            throw new FileSystemException(ErrorCode.FILE_SYSTEM_ERR_ACCOUNT_QR);
        }
    }

    public QRImage createReservationQRCodeImage() throws FileSystemException {
        try{
            return createQRCodeImage(reservationQRDir);
        } catch(QRNotCreatedException e) {
            throw new FileSystemException(ErrorCode.FILE_SYSTEM_ERR_RESERVATION_QR);
        }
    }

    private QRImage createQRCodeImage(String fixedDir) throws QRNotCreatedException{
        try {
            RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
            String content = generator.generate(30);
            log.info("content: {}", content);


            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT); //텍스트, 바코드 포맷,가로,세로

            MatrixToImageConfig config = new MatrixToImageConfig(DARK_COLOR, LIGHT_COLOR); //진한색, 연한색
            BufferedImage bufferedQrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

            /**
             * prefix content -> 다른 의미있는거로 변경하기
             */
            String fileOriName = UUID.randomUUID().toString();
            String destinationFileName = UUID.randomUUID().toString();
            String fileUrl = fixedDir + "/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth() + "/";
            log.info("fixedDir: {}", fixedDir);
            log.info("fileUrL: {}", fileUrl);
            log.info("destinationFileName: {}", destinationFileName);


            File destinationFile = new File(outbound + fileUrl + destinationFileName + EXTENSION);
            destinationFile.getParentFile().mkdirs();

            File file = new File(outbound + fileUrl + destinationFileName + EXTENSION);
//            File file = File.createTempFile(destinationFileName, EXTENSION, new File(outbound + fileUrl));
            ImageIO.write(bufferedQrImage, "png", file); //temp 위치에 qr이 이미지 생성됨.

            return QRImage.builder()
                    .fileLocalName(destinationFileName + EXTENSION)
                    .fileOriName(fileOriName + EXTENSION)
                    .fileUrl(inbound + fileUrl)
                    .content(content)
                    .build();

        } catch(WriterException | IOException e) {
            throw new QRNotCreatedException();
        }

    }
}
