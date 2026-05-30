package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.entity.Teacher;

public interface TeacherRepository
        extends JpaRepository<Teacher, Long> {
}
