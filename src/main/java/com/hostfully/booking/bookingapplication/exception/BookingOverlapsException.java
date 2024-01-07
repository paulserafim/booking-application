package com.hostfully.booking.bookingapplication.exception;

public class BookingOverlapsException extends RuntimeException{
    public BookingOverlapsException(String message) {
        super(message);
    }
}
