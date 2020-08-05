package com.github.holiver98.service;

import com.github.holiver98.model.Order;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;


//TODO: A Spring Mail-t esetleg érdemes megnézni, had csináljon meg mindent a Spring, neked meg csak a tényleges e-mail tartalommal kelljen foglalkozni.
public class MailService implements IMailService{
    private String senderEmailAddress = "";
    private String senderPassword = "";
    private final String senderInformationFilePath = "src/main/resources/emailsender.txt";

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
        Message msg = getMessage(content, session, emailAddress);
        Transport.send(msg);
    }

    @Override
    public void sendOrderConfirmationEmail(Order order) throws MessagingException {
        String content = String.format("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <title>Pizza Order</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Thank you for your order!</h1>\n" +
                "        <h2>Order:</h2>\n" +
                "        <ul><li>Id: %d</li><li>Date: %s</li><li>Total price: %.2f</li></ul>\n" +
                "    </body>\n" +
                "</html>", order.getId(), order.getDate(), order.getTotalPrice());
        sendMailTo(order.getUserEmailAddress(), content);
    }

    //TODO: +1, nem rossz gondolat, de talán feleslegesen bonyolult. Egy sima username, password paraméter elég, amit nem kell felvenni a property-kközé, hanem
    // Hanem indítási paraméterként kapja meg.
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
