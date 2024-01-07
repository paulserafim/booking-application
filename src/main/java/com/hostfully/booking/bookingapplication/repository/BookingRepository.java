package com.hostfully.booking.bookingapplication.repository;

import com.hostfully.booking.bookingapplication.model.DAO.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking getBookingById(Long id);
    Booking save(Booking booking);
    void delete(Booking booking);
    List<Booking> getBookingByPropertyId(Long propertyId);
}
