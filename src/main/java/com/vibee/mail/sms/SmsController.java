package com.vibee.mail.sms;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SmsController {


    @Value("${twilo.username}")
    String twilousername;

    @Value("${twilo.password}")
    String twilopassword;

    @Value("${twilo.sender.phonenumber}")
    String twilosendernumber;

    @Value("${twilo.reciver.phonenumber}")
    String twilorecivernumber;



    //Message send to mobile
    public String  sendMessages(String messagebody) {

        Twilio.init(twilousername,twilopassword);

        Message message = Message.creator(
                new PhoneNumber(twilorecivernumber),
                new PhoneNumber(twilosendernumber),
                messagebody).create();

        System.out.println("Sent message w/ sid: " + message.getSid());

        return message.getBody();
    }



}
