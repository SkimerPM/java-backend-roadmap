package org.example.notifications;

public interface Cancelable {
    void cancel();
    boolean isCancelled();
}
