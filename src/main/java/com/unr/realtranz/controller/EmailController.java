package com.unr.realtranz.controller;

import com.unr.realtranz.model.EmailDetails;
import com.unr.realtranz.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/***
 **@Author:Nagaraju Ukkalam
 @Version:1.0
 @Date:27-01-2023 01:39
 **/
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value="/sendMail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {
                    MediaType.APPLICATION_ATOM_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public ModelAndView sendMail(EmailDetails details) {
        String status = emailService.receiveSimpleMail(details);

        ModelMap modelMap = new ModelMap("successEnq","Message Sent Successfully");
        return new ModelAndView("redirect:/",modelMap);
    }

    @RequestMapping(value="/sendMailWithAttachment", method = RequestMethod.POST)
    public String sendMailWithAttachment(
            @RequestBody EmailDetails details) {
        String status = emailService.receiveMailWithAttachment(details);
        return status;
    }
}
