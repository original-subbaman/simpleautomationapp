package com.mact.simpleautomationapp.Services;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class SendMailJobService implements Runnable {
    private Message message;

    public SendMailJobService(Message message){
        this.message = message;
    }
    @Override
    public void run() {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
