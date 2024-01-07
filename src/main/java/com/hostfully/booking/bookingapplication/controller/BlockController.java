package com.hostfully.booking.bookingapplication.controller;

import com.hostfully.booking.bookingapplication.exception.BlockNotFoundException;
import com.hostfully.booking.bookingapplication.model.DAO.Block;
import com.hostfully.booking.bookingapplication.service.BlockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
public class BlockController {
    private BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping("/blocks/{id}")
    public ResponseEntity<Object> getBlockById(@PathVariable Long id) {
        Block block = blockService.getBlockById(id);

        if(block == null)
            throw new BlockNotFoundException("id:" + id + " not found for this block");

        return ResponseEntity.ok(block);
    }

    @PostMapping("/blocks")
    public ResponseEntity<Object> createBlock(@RequestBody Block block) {
        Block savedBlock = blockService.save(block);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedBlock.getId())
                        .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/blocks/{id}")
    public ResponseEntity<Object> updateBlock(@PathVariable Long id, @RequestBody Block block) {
        Block updatedBlock = blockService.update(block, id);

        if(updatedBlock == null)
            throw new BlockNotFoundException("id:" + id + " not found for this block");

        return ResponseEntity.ok(updatedBlock);
    }

    @DeleteMapping("/blocks/{id}")
    public ResponseEntity<Object> deleteBlock(@PathVariable Long id) {
        Block block = blockService.getBlockById(id);

        if(block == null)
            throw new BlockNotFoundException("id:" + id + " not found for this block");

        blockService.delete(block);

        return ResponseEntity.noContent().build();
    }
}
