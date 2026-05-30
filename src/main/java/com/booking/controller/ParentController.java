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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @GetMapping("/offerings")
    public List<OfferingResponse>
    getAvailableOfferings(
            @RequestParam Long parentId) {

        return parentService
                .getAvailableOfferings(
                        parentId);
    }

    @PostMapping("/book")
    public String bookOffering(
            @Valid
            @RequestBody
            BookOfferingRequest request) {

        parentService.bookOffering(
                request);

        return "Offering Booked Successfully";
    }

    @GetMapping("/{parentId}/bookings")
    public List<OfferingResponse>
    getBookings(
            @PathVariable Long parentId) {

        return parentService
                .getBookings(parentId);
    }
}
