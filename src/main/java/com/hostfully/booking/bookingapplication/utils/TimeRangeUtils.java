package com.hostfully.booking.bookingapplication.utils;

import com.hostfully.booking.bookingapplication.model.DAO.TimeRange;

public class TimeRangeUtils {
    public static <T extends TimeRange> boolean overlaps(T range1, T range2) {
        return range1.getStartDate().isBefore(range2.getEndDate()) &&
                range2.getStartDate().isBefore(range1.getEndDate());
    }
}