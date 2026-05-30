package com.booking.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.dto.BookOfferingRequest;
import com.booking.dto.OfferingResponse;
import com.booking.service.ParentService;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
@Tag(name = "Parent APIs", description = "Parent Booking APIs")
public class ParentController {

    private final ParentService parentService;

    @Operation(summary = "Get Available Offerings")
    @GetMapping("/offerings")
    public List<OfferingResponse> getAvailableOfferings(
            @RequestParam Long parentId) {

        return parentService.getAvailableOfferings(parentId);
    }

    @Operation(summary = "Book Offering")
    @PostMapping("/book")
    public String bookOffering(
            @RequestBody BookOfferingRequest request) {

        parentService.bookOffering(request);

        return "Offering Booked Successfully";
    }

    @Operation(summary = "Get Parent Bookings")
    @GetMapping("/{parentId}/bookings")
    public List<OfferingResponse> getBookings(
            @PathVariable Long parentId) {

        return parentService.getBookings(parentId);
    }
}