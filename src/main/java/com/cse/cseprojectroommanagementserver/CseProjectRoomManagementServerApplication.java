package com.cse.cseprojectroommanagementserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CseProjectRoomManagementServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CseProjectRoomManagementServerApplication.class, args);
    }

}
