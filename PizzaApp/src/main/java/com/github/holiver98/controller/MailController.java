package com.github.holiver98.controller;

import com.github.holiver98.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private IMailService mailService;

    @PostMapping("/send")
    public void sendMail(@RequestBody MailInfo mailinfo) throws MessagingException {
        mailService.sendMailTo(mailinfo.email, mailinfo.content);
    }

    private static class MailInfo{
        public String email;
        public String content;
    }
}
