package com.github.holiver98.service;

import com.github.holiver98.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

public class MailService implements IMailService{
    private String senderEmailAddress = "";
    private String senderPassword = "";
    private static final String senderInformationFilePath = "src/main/resources/emailsender.txt";
    private static final boolean debug_SendToSelf = true;

    @Autowired
    TemplateEngine templateEngine;

    public MailService(){
        tryLoadEmailAndPasswordFromFile();
    }

    @Override
    public void sendMailTo(String emailAddress, String content) throws MessagingException {
        Properties props = getMailProperties();
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmailAddress, senderPassword);
            }
        });

        String recipientEmail = emailAddress;
        if(debug_SendToSelf){
            recipientEmail = senderEmailAddress;
        }

        Message msg = getMessage(content, session, recipientEmail);
        Transport.send(msg);
    }

    @Override
    public void sendOrderConfirmationEmail(Order order) throws MessagingException {
        final Context context = new Context();
        context.setVariable("order", order);
        String body = templateEngine.process("email/order-email-template", context);
        sendMailTo(order.getUserEmailAddress(), body);
    }

    private void tryLoadEmailAndPasswordFromFile() {
        try {
            loadEmailAndPasswordFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEmailAndPasswordFromFile() throws IOException {
        FileInputStream fis = new FileInputStream(senderInformationFilePath);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        senderEmailAddress = br.readLine();
        senderPassword = br.readLine();
        fis.close();
        isr.close();
        br.close();
    }

    private Properties getMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    private Message getMessage(String content, Session session, String toEmail) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(senderEmailAddress, false));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        msg.setSubject("Teszt");
        msg.setContent(content, "text/html");
        msg.setSentDate(new Date());
        return msg;
    }
}
