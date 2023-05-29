package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.FindUserByCategoryIdModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailNewRecipeProducer {
    private String exchangeRecipe = "exchangeRecipe";
    private String bindingKeyNewRecipe = "bindingKeyNewRecipe";
    private final RabbitTemplate rabbitTemplate;
    public void sendNewRecipe(FindUserByCategoryIdModel model){
        rabbitTemplate.convertAndSend(exchangeRecipe,bindingKeyNewRecipe,model);
    }
}
