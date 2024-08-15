package Application.integration;

import Application.Event.CustomEvent;
import framework.annotation.EventListener;
import framework.annotation.Service;

@Service
public class EmailSender {
    @EventListener
    public void sendEmailEvent(CustomEvent event) {
        System.out.println("EmailSender: sendEmailEvent listener: message="+event.getMessage());
    }
}
