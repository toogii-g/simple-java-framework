package Application.integration;

import Application.Event.CustomEvent;
import framework.annotation.EventListener;
import framework.annotation.Service;

@Service
public class Logger implements ILogger {
    public void log(String message){
       // System.out.println("Logger: logging="+message);
    }

    @EventListener
    public void logEvent(CustomEvent event){
        System.out.println("Logger: logEvent listener: message="+event.getMessage());
    }
}
