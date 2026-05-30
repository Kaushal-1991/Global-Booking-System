package com.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.entity.Course;

public interface CourseRepository
        extends JpaRepository<Course, Long> {
}
