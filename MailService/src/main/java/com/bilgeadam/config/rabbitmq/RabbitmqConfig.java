package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    private String queueRegister = "queueRegister";
    @Bean
    Queue registerQueue(){
        return new Queue(queueRegister);
    }
    private String queueForgotPassword = "queueForgotPassword";
    @Bean
    Queue forgotPasswordQueue(){
        return new Queue(queueForgotPassword);
    }
    // recipe-service
    private String queueNewRecipe = "queueNewRecipe";
    @Bean
    Queue queueNewRecipe(){
        return new Queue(queueNewRecipe);
    }
}
