package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.ForgotPasswordModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailForgotPasswordConsumer {
    private final MailService mailService;
    @RabbitListener(queues = "queueForgotPassword")
    public void sendMailForgotPassword(ForgotPasswordModel model){
        mailService.sendForgotPassword(model);
    }
}
