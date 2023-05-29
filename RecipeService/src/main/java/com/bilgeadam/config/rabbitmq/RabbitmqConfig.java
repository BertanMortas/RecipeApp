package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    private String exchangeRecipe = "exchangeRecipe";
    @Bean
    DirectExchange exchangeRecipe(){
        return new DirectExchange(exchangeRecipe);
    }
    // mail new recipes by users favorite list
    private String queueNewRecipe = "queueNewRecipe";
    private String bindingKeyNewRecipe = "bindingKeyNewRecipe";
    @Bean
    Queue queueNewRecipe(){
        return new Queue(queueNewRecipe);
    }
    @Bean
    public Binding bindingRegister(final Queue queueNewRecipe, final DirectExchange exchangeRecipe){
        return BindingBuilder.bind(queueNewRecipe).to(exchangeRecipe).with(bindingKeyNewRecipe);
    }
}
