package com.cse.cseprojectroommanagementserver.global.util;

import com.cse.cseprojectroommanagementserver.domain.tablereturn.exception.ImageUploadFailException;
import com.cse.cseprojectroommanagementserver.global.dto.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileUploadUtil {

    @Value("${fileDir.inbound}")
    private String inbound;

    @Value("${fileDir.outbound}")
    private String outbound;

    @Value("${fileDir.returns}")
    private String returns;

    @Value("${fileDir.complains}")
    private String complains;

    public Image uploadReturnsImage(MultipartFile multipartFile) {
        return uploadFile(multipartFile, returns);
    }

    public Image uploadComplainsImage(MultipartFile multipartFile) {
        return uploadFile(multipartFile, complains);
    }

    private Image uploadFile(MultipartFile multipartFile, String path) {
        if(multipartFile == null) {
            return null;
        }
        try {
            String fileOriName = multipartFile.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileOriName);

            String fileUrl = path + "/" + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth() + "/";

            String destinationFileName = UUID.randomUUID() + "." + fileExtension;
            File destinationFile = new File(outbound + fileUrl + destinationFileName);
            destinationFile.getParentFile().mkdirs();
            multipartFile.transferTo(destinationFile);

            return Image.builder()
                    .fileLocalName(destinationFileName)
                    .fileOriName(fileOriName)
                    .fileUrl(inbound + fileUrl)
                    .build();

        } catch (Exception e) {
            throw new ImageUploadFailException();
        }
    }


}
