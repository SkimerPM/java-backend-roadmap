package org.example.notifications;

public abstract class Notification {
    protected final String message;
    protected Notification(String message){
        if(message == null){
            throw new IllegalArgumentException("Message can't be null");
        }
        String cleaned = message.strip();
        if (cleaned.isBlank()){
            throw new IllegalArgumentException("Message can't be empty");
        }
        this.message = cleaned;
    }
    String getMessage(){
        return this.message;
    }
    public abstract String format();
}
