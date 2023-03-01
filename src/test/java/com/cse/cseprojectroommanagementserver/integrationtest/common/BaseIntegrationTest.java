package com.cse.cseprojectroommanagementserver.integrationtest.common;

import com.cse.cseprojectroommanagementserver.global.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Disabled
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class BaseIntegrationTest {

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
}
