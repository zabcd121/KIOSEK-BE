package com.cse.cseprojectroommanagementserver.global.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class QRGenerator {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final int DARK_COLOR = 0x00000000;
    private static final int LIGHT_COLOR = 0xFFFFFFFF;


    private final String accountQRDir;

    public QRGenerator(@Value("${file,account-qr-dir}") String qrDir) {
        this.accountQRDir = qrDir;
    }
    
    // QR코드 이미지 생성
//    public static String getQRCodeImage(String text, int width, int height, int qrDarkColor, int qrLightColor) throws WriterException, IOException {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
//
//        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//
//        return Base64.getEncoder().encodeToString(pngOutputStream.toByteArray());
//    }

    public void createAccountQRCodeImage(String content) throws WriterException, IOException {
        createQRCodeImage(content, WIDTH, HEIGHT, DARK_COLOR, LIGHT_COLOR);
    }

    private void createQRCodeImage(String content, int width, int height, int qrDarkColor, int qrLightColor) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height); //텍스트, 바코드 포맷,가로,세로

        MatrixToImageConfig config = new MatrixToImageConfig(qrDarkColor , qrLightColor); //진한색, 연한색
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix , config);

        File file =  File.createTempFile(content, ".png", new File(accountQRDir));
        ImageIO.write(qrImage, "png", file); //temp 위치에 qr이 이미지 생성됨.
        InputStream is = new FileInputStream(file.getAbsolutePath()); //S3에 업로드 하기위한 작업

        is.close();
    }
}
