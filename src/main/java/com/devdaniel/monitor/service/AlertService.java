package com.devdaniel.monitor.service;

import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final JavaMailSender sender;
    private final DiscordService discordService;
    private final UserRepository userRepository;

    @Value("${spring.mail.from:no-reply@monitorweb.com}")
    private String fromEmail;

    public void sendAlertToUser(User user, String mensagem) {
        // sendEmail(user, mensagem);
        //sendDiscordAlert(user, mensagem);
    }

    //private void sendEmail(User user, String mensagem) {
    //    SimpleMailMessage email = new SimpleMailMessage();
    //    email.setFrom(fromEmail);
    //    email.setTo("teste");
    //    email.setSubject("[MonitorWeb] Alerta de Site");
    //    email.setText(mensagem);
    //    sender.send(email);
    //}

    //private void sendDiscordAlert(User user, String mensagem) {
    //    String channelId = user.getDiscordChannelId();
    //    if (channelId != null && !channelId.isBlank()) {
    //        discordService.sendMessageToChannel(channelId, mensagem);
    //    } else {
    //        System.out.println("⚠️ Usuário " + user.getUsername() + " não tem canal Discord configurado.");
    //    }
    //}
}