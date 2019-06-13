package com.junyangcompany.demo.security.service;

import com.junyangcompany.demo.security.mapping.email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * author:pan le
 * Date:2019/4/18
 * Time:16:28
 */
@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    public String USER_NAME;//发送者

    @Value("${spring.mail.password}")
    public String password;//发送者密码

//    void send(email email) throws MessagingException {
//
//        final Properties props = new Properties();
//        // 表示SMTP发送邮件，需要进行身份验证
//        props.setProperty("mail.transport.protocol", "smtp");// 设置传输协议
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.host", "smtp.qq.com");//QQ邮箱的服务器 如果是企业邮箱或者其他邮箱得更换该服务器地址
//        // 发件人的账号
//        props.put("mail.user", USER_NAME);
//        // 访问SMTP服务时需要提供的密码
////        props.put("mail.password", password);
////        props.put("mail.smtp.port", "465");
////        props.put("mail.smtp.socketFactory.port", "465");
////        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
////        props.put("mail.smtp.socketFactory.fallback", "false");
//
//        // 构建授权信息，用于进行SMTP进行身份验证
//        Authenticator authenticator = new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                // 用户名、密码
//                String userName = props.getProperty("mail.user");
//                String password = props.getProperty("mail.password");
//                return new PasswordAuthentication(userName, password);
//            }
//        };
//        Session mailSession = Session.getInstance(props, authenticator);
//        MimeMessage mimeMessage = new MimeMessage(mailSession);
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
//        helper.setFrom(USER_NAME);
//        helper.setTo(email.getUserEmail());
//        mimeMessage.setContent(email.getContent(),"text/html;charset=UTF-8");
//        helper.setSubject(email.getSubject());
//        sender.send(mimeMessage);
//    }

    public void send(email email) {
        MimeMessage message = sender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(USER_NAME);
            helper.setTo(email.getUserEmail());
            helper.setSubject(email.getSubject());
            helper.setText(email.getContent(), true);
            sender.send(message);
            log.info("html邮件发送成功");
        } catch (MessagingException e) {
            log.error("发送html邮件时发生异常！", e);
        }
    }
}
