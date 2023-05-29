package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.ForgotPasswordModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailForgotPasswordProducer {
    private String bindingKeyForgotPassword = "bindingKeyForgotPassword";
    private String exchange = "exchangeAuth";
    private final RabbitTemplate rabbitTemplate;
    public void sendForgotPassword(ForgotPasswordModel model){
        rabbitTemplate.convertAndSend(exchange,bindingKeyForgotPassword,model);
    }
}
