package com.bilgeadam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserprofileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserprofileServiceApplication.class);
    }
}