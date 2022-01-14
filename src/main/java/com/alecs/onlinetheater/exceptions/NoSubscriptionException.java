package com.alecs.onlinetheater.exceptions;

public class NoSubscriptionException extends RuntimeException {
    public NoSubscriptionException (String message) {
        super(message);
    }
}
