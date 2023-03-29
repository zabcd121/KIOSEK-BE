package com.cse.cseprojectroommanagementserver.unittest;

import com.cse.cseprojectroommanagementserver.global.util.AES256;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AESTest {

    @Autowired
    AES256 aes256;

    @Test
    void encrypt() throws Exception {
        // Given
        String enc = aes256.encrypt("text");
        System.out.println(enc);

        String decrypt = aes256.decrypt(enc);
        System.out.println(decrypt);

        // When

        // Then
    }
}
