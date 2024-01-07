package com.hostfully.booking.bookingapplication.repository;

import com.hostfully.booking.bookingapplication.model.DAO.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    Block getBlockById(Long id);
    Block save(Block block);
    void delete(Block block);
    List<Block> getBlocksByPropertyId(Long propertyId);
}
