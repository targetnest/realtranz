package com.unr.realtranz.service.impl;

import com.unr.realtranz.model.EmailDetails;
import com.unr.realtranz.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:27-01-2023 01:35
 **/
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String receiveSimpleMail(EmailDetails details) {

        // Try block to check for exceptions
        try {

            MimeMessage message = javaMailSender.createMimeMessage();

            message.setSubject(details.getSubject());
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(details.getRecipient());
            helper.setTo(sender);
            helper.setText(details.getMsgBody(), true);
            javaMailSender.send(message);

        /*    // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom();
            mailMessage.setTo(sender);
            mailMessage.setText(details.getMsgBody(),);
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);*/
            return "Mail Sent Successfully...";
        }

        catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }

    public String
    receiveMailWithAttachment(EmailDetails details) {
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            FileSystemResource file = new FileSystemResource( new File(details.getAttachment()));

            mimeMessageHelper.addAttachment( file.getFilename(), file);

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }

        catch (MessagingException e) {
            return "Error while sending mail!!!";
        }
    }
}
