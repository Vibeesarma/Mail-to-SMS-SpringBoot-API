package com.vibee.mail.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.springframework.integration", "com.vibee.mail.sms"})
public class SmsApiApplication {

    public static void main(String[] args)  {SpringApplication.run(SmsApiApplication.class, args);}
}
