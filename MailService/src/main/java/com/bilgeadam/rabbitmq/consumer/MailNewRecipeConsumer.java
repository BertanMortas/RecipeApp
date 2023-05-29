package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.FindUserByCategoryIdModel;
import com.bilgeadam.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailNewRecipeConsumer {
    private final MailService mailService;
    @RabbitListener(queues = "queueNewRecipe")
    public void sendNewRecipe(FindUserByCategoryIdModel model){
        mailService.sendNewRecipe(model);
    }
}
