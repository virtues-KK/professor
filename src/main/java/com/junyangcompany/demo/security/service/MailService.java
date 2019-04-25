package com.junyangcompany.demo.security.service;

import com.junyangcompany.demo.security.mapping.email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * author:pan le
 * Date:2019/4/18
 * Time:16:28
 */
@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    public String USER_NAME;//发送者

    void send(email email) throws MessagingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(USER_NAME);
        helper.setTo(email.getUserEmail());
        mimeMessage.setContent(email.getContent(),"text/html;charset=UTF-8");
        helper.setSubject(email.getSubject());
        sender.send(mimeMessage);
    }

}
