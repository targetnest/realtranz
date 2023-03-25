package com.unr.realtranz.service;

import com.unr.realtranz.model.EmailDetails;

public interface EmailService {

    String receiveSimpleMail(EmailDetails details);

    String receiveMailWithAttachment(EmailDetails details);
}
