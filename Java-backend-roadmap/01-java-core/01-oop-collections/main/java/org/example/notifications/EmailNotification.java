package org.example.notifications;

import org.example.valueobjects.Email;

public class EmailNotification extends Notification implements Cancelable{
    private final Email email;
    private boolean canceled = false;

    public EmailNotification(Email email, String message){
        super(message);
        if(email == null){
            throw new IllegalArgumentException("Email cannot be null.");
        }
        this.email = email;
    }
    @Override
    public void cancel(){
        canceled = true;
    }
    @Override
    public boolean isCancelled(){
        return canceled;
    }
    @Override
    public String format() {
        return "Email to " + email.value() + ": " + message;
    }
    @Override
    public String toString(){
        return format() + " [" + (canceled ? "Canceled" : "Active") + "]";
    }
}
