package com.cse.cseprojectroommanagementserver.integrationtest.common;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc(addFilters = false)
public class BaseIntegrationTestWithNoFilter extends BaseIntegrationTest{
}
