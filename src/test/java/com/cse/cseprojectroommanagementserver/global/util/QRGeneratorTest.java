package com.cse.cseprojectroommanagementserver.global.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QRGeneratorTest {

    @Autowired
    private QRGenerator qrGenerator;


    @Test
    public void QR_반환값확인() throws Exception {
        //given
        String loginId = "20180335";
        String secretKey = "lkhasdjlkjasd";
        String text = (loginId+secretKey);.

        //when
        qrGenerator.createAccountQRCodeImage(text);

    }

}