package com.cse.cseprojectroommanagementserver.integrationtest.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@Disabled
@AutoConfigureMockMvc
@Transactional
public class BaseIntegrationTestWithIgnoringURI { // security config에서 ignoring 설정된 api 테스트할 때 사용

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
