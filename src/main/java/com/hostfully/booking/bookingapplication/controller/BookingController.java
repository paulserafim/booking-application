package com.hostfully.booking.bookingapplication.controller;

import com.hostfully.booking.bookingapplication.exception.BookingNotFoundException;
import com.hostfully.booking.bookingapplication.model.DAO.Booking;
import com.hostfully.booking.bookingapplication.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
public class BookingController {
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Object> getBookingById(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);

        if(booking == null)
            throw new BookingNotFoundException("id:" + id + " not found for this booking");

        return ResponseEntity.ok(booking);
    }

    @PostMapping("/bookings")
    public ResponseEntity<Object> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.save(booking);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBooking.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<Object> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.update(booking, id);

        if(updatedBooking == null)
            throw new BookingNotFoundException("id:" + id + " not found for this booking");

        return ResponseEntity.ok(updatedBooking);
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Long id) {
        Booking booking = bookingService.getBookingById(id);

        if(booking == null)
            throw new BookingNotFoundException("id:" + id + " not found for this booking");

        bookingService.delete(booking);
        return ResponseEntity.noContent().build();
    }
}
