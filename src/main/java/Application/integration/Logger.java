package Application.integration;

import framework.annotation.Service;

@Service
public class Logger implements ILogger {
    public void log(String message){
        System.out.println("Logger: logging="+message);
    }
}
