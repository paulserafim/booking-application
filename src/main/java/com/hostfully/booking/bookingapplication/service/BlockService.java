package com.hostfully.booking.bookingapplication.service;

import com.hostfully.booking.bookingapplication.exception.BlockOverlapsException;
import com.hostfully.booking.bookingapplication.model.DAO.Block;
import com.hostfully.booking.bookingapplication.model.DAO.Booking;
import com.hostfully.booking.bookingapplication.model.DAO.TimeRange;
import com.hostfully.booking.bookingapplication.repository.BlockRepository;
import com.hostfully.booking.bookingapplication.repository.BookingRepository;
import com.hostfully.booking.bookingapplication.utils.TimeRangeUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlockService {

    private BlockRepository blockRepository;
    private BookingRepository bookingRepository;

    public BlockService(BlockRepository blockRepository, BookingRepository bookingRepository) {
        this.blockRepository = blockRepository;
        this.bookingRepository = bookingRepository;
    }

    public Block getBlockById(Long id) {
        Optional<Block> blockOptional = blockRepository.findById(id);
        return blockOptional.orElse(null);
    }

    public Block save(Block block) {
        List<Block> blocksByProperty = blockRepository.getBlocksByPropertyId(block.getPropertyId());
        List<Booking> bookingByProperty = bookingRepository.getBookingByPropertyId(block.getPropertyId());
        for (int index = 0; index < blocksByProperty.size(); index++) {
            if (TimeRangeUtils.overlaps(block, blocksByProperty.get(index)) && block.getId() != blocksByProperty.get(index).getId()) {
                throw new BlockOverlapsException("This block" + " overlaps with block id:" + blocksByProperty.get(index).getId());

            }
        }

        for (int index = 0; index < bookingByProperty.size(); index++) {
            if (TimeRangeUtils.overlaps(block, bookingByProperty.get(index)) && block.getId() != blocksByProperty.get(index).getId()) {
                throw new BlockOverlapsException("This block" + " overlaps with booking id:" + bookingByProperty.get(index).getId());

            }
        }

        return blockRepository.save(block);
    }

    public Block update(Block block, Long id) {
        Optional<Block> blockOptional = blockRepository.findById(id);
        if (blockOptional.isPresent()) {
            block.setId(id);
            Block updatedBlock = blockRepository.save(block);
            return updatedBlock;
        } else return null;
    }

    public void delete(Block block) {
        blockRepository.delete(block);
    }
}
