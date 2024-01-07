package com.hostfully.booking.bookingapplication.service;

import com.hostfully.booking.bookingapplication.exception.BlockOverlapsException;
import com.hostfully.booking.bookingapplication.exception.BookingOverlapsException;
import com.hostfully.booking.bookingapplication.exception.BookingOverlapsException;
import com.hostfully.booking.bookingapplication.model.DAO.Block;
import com.hostfully.booking.bookingapplication.model.DAO.Booking;
import com.hostfully.booking.bookingapplication.model.DAO.Booking;
import com.hostfully.booking.bookingapplication.model.DAO.BookingStatus;
import com.hostfully.booking.bookingapplication.repository.BlockRepository;
import com.hostfully.booking.bookingapplication.repository.BookingRepository;
import com.hostfully.booking.bookingapplication.utils.TimeRangeUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private BookingRepository bookingRepository;
    private BlockRepository blockRepository;

    public BookingService (BookingRepository bookingRepository, BlockRepository blockRepository) {
        this.bookingRepository = bookingRepository;
        this.blockRepository = blockRepository;
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.getBookingById(id);
    }

    public Booking save(Booking booking) {
        List<Block> blocksByProperty = blockRepository.getBlocksByPropertyId(booking.getPropertyId());
        List<Booking> bookingByProperty = bookingRepository.getBookingByPropertyId(booking.getPropertyId());
        for(int index = 0; index < blocksByProperty.size(); index ++) {
            if(TimeRangeUtils.overlaps(booking, blocksByProperty.get(index))) {
                throw new BookingOverlapsException("This booking "
                        + "overlaps with block id:"
                        + blocksByProperty.get(index).getId());

            }
        }

        for(int index = 0; index < bookingByProperty.size(); index ++) {
            if(TimeRangeUtils.overlaps(booking, bookingByProperty.get(index))) {
                throw new BookingOverlapsException("This booking "
                        + "overlaps with booking id:"
                        + bookingByProperty.get(index).getId());

            }
        }
        return bookingRepository.save(booking);
    }

    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    public Booking update(Booking booking, Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if(bookingOptional.isPresent()) {
            booking.setId(id);
            booking.setStatus(BookingStatus.UPDATED);
            Booking updatedBooking = bookingRepository.save(booking);
            return updatedBooking;
        } else
            return null;
    }
}