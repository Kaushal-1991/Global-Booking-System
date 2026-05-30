package com.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.entity.Booking;
import com.booking.entity.Session;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    boolean existsByParentIdAndOfferingId(
            Long parentId,
            Long offeringId
    );

    List<Booking> findByParentId(Long parentId);

    @Query("""
           select s
           from Booking b
           join b.offering o
           join o.sessions s
           where b.parent.id = :parentId
           """)
    List<Session> findBookedSessions(
            @Param("parentId") Long parentId
    );
}