package com.hostfully.booking.bookingapplication.model.DAO;

import java.time.LocalDate;

public interface TimeRange {
    LocalDate getStartDate();
    LocalDate getEndDate();
}
