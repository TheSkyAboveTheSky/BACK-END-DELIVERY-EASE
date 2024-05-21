package com.project.isima.exceptions;

public class DeliveryNotFoundException extends RuntimeException{
    public DeliveryNotFoundException(String message) {
        super(message);
    }
}
