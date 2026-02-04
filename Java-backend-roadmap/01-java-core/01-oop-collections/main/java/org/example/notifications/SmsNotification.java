package org.example.notifications;

import org.example.valueobjects.PhoneNumber;

public class SmsNotification extends Notification implements Cancelable{
    private final PhoneNumber phone;
    private boolean canceled = false;
    public SmsNotification(PhoneNumber phonenumber, String message){
        super(message);
        if(phonenumber == null){
            throw new IllegalArgumentException("Phone cannot be null.");
        }
        this.phone = phonenumber;
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
        return "SMS to " + phone.value() + ": " + message;
    }
}
