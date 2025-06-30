package com.devdaniel.Monitor.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    @Autowired
    private JavaMailSender sender;

    public void sendEmail(String mensagem) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("from@example.com");
        email.setTo("c0a9598354-d909fb@inbox.mailtrap.io");
        email.setSubject("[MonitorWeb] Alerta de Site");
        email.setText(mensagem);
        sender.send(email);
    }

    @PostConstruct
    public void testEmailOnStartup() {
        try {
            sendEmail("✅ Teste automático: o serviço de monitoramento foi iniciado com sucesso.");
        } catch (Exception e) {
            System.err.println("Falha ao enviar email no startup: " + e.getMessage());
        }
    }

}
