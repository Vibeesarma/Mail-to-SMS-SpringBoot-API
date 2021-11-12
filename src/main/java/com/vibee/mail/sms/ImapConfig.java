package com.vibee.mail.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mail.ImapIdleChannelAdapter;
import org.springframework.integration.mail.ImapMailReceiver;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import static org.apache.logging.log4j.status.StatusLogger.getLogger;

@EnableIntegration
@Configuration
@ConfigurationProperties(prefix = "imap")
 class ImapConfig{

    @Autowired
    SmsController smsController;

    private String MailReceiverURL;

    public String getMailReceiverURL() {
        return MailReceiverURL;
    }

    public void setMailReceiverURL(String mailReceiverURL) {
        MailReceiverURL = mailReceiverURL;
    }


    @Bean(name="imapChannel")
        public DirectChannel directChannel() {
            return new DirectChannel();
        }


        private Properties javaMailProperties() {
            Properties javaMailProperties = new Properties();

            javaMailProperties.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            javaMailProperties.setProperty("mail.imap.socketFactory.fallback", "false");
            javaMailProperties.setProperty("mail.store.protocol", "imaps");
            javaMailProperties.setProperty("mail.debug", "true");
            javaMailProperties.setProperty("mail.smtp.timeout", "10000");

            return javaMailProperties;
        }


        @Bean
        ImapMailReceiver mailReceiver() {
            ImapMailReceiver mailReceiver = new ImapMailReceiver(MailReceiverURL);
            mailReceiver.setJavaMailProperties(javaMailProperties());
            mailReceiver.setShouldDeleteMessages(false);
            mailReceiver.setShouldMarkMessagesAsRead(true);
            return mailReceiver;
        }


        @Bean
        ImapIdleChannelAdapter mailAdapter() {
            ImapIdleChannelAdapter imapIdleChannelAdapter = new ImapIdleChannelAdapter(mailReceiver());
            imapIdleChannelAdapter.setAutoStartup(true);
            imapIdleChannelAdapter.setOutputChannel(directChannel());
            return imapIdleChannelAdapter;
        }


        @ServiceActivator(inputChannel = "imapChannel")
        public void handleMessage(MimeMessage message) throws MessagingException, IOException {
            getLogger().info("Got message!");

            String subject = message.getSubject();
            getLogger().info("Subject: {}", subject);
            String contentType = message.getContentType();
            getLogger().info("ContentType: {}", contentType);

            String email =message.getMessageID();

            String fromEmail = message.getHeader("From")[0].split("<")[1].split(">")[0];
            String sendBody ="the subject of the message is "+subject+".\n come from : "+fromEmail;


            smsController.sendMessages(sendBody);


            Object content = message.getContent();
            if (content instanceof String) {
                String text = (String) content;
                getLogger().info("Content: {}", text);
                getLogger().info("Length: {}", text.length());
            } else {
                getLogger().info("Other content: {}", content);
            }

        }
    }


