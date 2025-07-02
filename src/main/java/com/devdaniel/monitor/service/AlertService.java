package com.devdaniel.monitor.service;

import com.devdaniel.monitor.model.User;
import com.devdaniel.monitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final JavaMailSender sender;
    private final DiscordService discordService;
    private final UserRepository userRepository;

    public void sendAlertToUser(User user, String mensagem) {
        //sendEmail(mensagem);
        sendDiscordAlert(user, mensagem);
    }

    private void sendEmail(String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("from@example.com");
        email.setTo("c0a9598354-d909fb@inbox.mailtrap.io");
        email.setSubject("[MonitorWeb] Alerta de Site");
        email.setText(mensagem);
        sender.send(email);
    }

    private void sendDiscordAlert(User user, String mensagem) {
        String channelId = user.getDiscordChannelId();
        if (channelId != null && !channelId.isBlank()) {
            discordService.sendMessageToChannel(channelId, mensagem);
        } else {
            System.out.println("Usuário " + user.getUsername() + " não tem canal Discord configurado.");
        }
    }
}