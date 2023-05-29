package com.bilgeadam.service;

import com.bilgeadam.rabbitmq.model.FindUserByCategoryIdModel;
import com.bilgeadam.rabbitmq.model.ForgotPasswordModel;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    public void sendRegisterMail (MailRegisterModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Aktivasyon Kodu");
        mailMessage.setText("Sayın, "+ model.getUsername()+ " üyelik şifreniz: "+model.getActivationCode());
        javaMailSender.send(mailMessage);
    }

    public void sendForgotPassword(ForgotPasswordModel model) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Aktivasyon Kodu");
        mailMessage.setText("Sayın, "+ model.getUsername()+ " yeni şifreniz: "+model.getNewPassword());
        javaMailSender.send(mailMessage);
    }

    public void sendNewRecipe(FindUserByCategoryIdModel model) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("New Recipe");
        mailMessage.setText("Sayın, "+ model.getUsername()+ " beğendiğiniz kategoriye yeni bir tarif eklenmiştir: "+model.getRecipeName());
        javaMailSender.send(mailMessage);
    }
}
