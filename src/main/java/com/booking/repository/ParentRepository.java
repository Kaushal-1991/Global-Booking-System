package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.booking.entity.Parent;

import jakarta.persistence.LockModeType;

public interface ParentRepository extends JpaRepository<Parent, Long> {

@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("""
    select p
    from Parent p
    where p.id=:parentId
    """)
Parent lockParent(Long parentId);
}