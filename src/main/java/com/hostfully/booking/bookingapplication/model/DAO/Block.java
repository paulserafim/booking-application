package com.hostfully.booking.bookingapplication.model.DAO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Block implements TimeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Long propertyId;

    public Block(LocalDate startDate, LocalDate endDate, Long propertyId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.propertyId = propertyId;
    }

    public Block() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public Long getPropertyId() { return propertyId; }

    public void setPropertyId(Long propertyId) { this.propertyId = propertyId; }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
