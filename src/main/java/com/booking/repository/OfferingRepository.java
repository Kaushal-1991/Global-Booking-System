package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.entity.Offering;

import java.util.List;

public interface OfferingRepository
        extends JpaRepository<Offering, Long> {

    List<Offering> findByTeacherId(Long teacherId);
}