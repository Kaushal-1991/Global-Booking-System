package com.booking.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.dto.AddSessionRequest;
import com.booking.dto.CreateOfferingRequest;
import com.booking.dto.OfferingResponse;
import com.booking.service.TeacherService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/offerings")
    public Long createOffering(
            @Valid
            @RequestBody
            CreateOfferingRequest request) {

        return teacherService
                .createOffering(request);
    }

    @PostMapping(
            "/offerings/{offeringId}/sessions")
    public String addSessions(
            @PathVariable Long offeringId,
            @RequestBody AddSessionRequest request) {

        teacherService.addSessions(
                offeringId,
                request);

        return "Sessions Added Successfully";
    }

    @GetMapping("/{teacherId}/offerings")
    public List<OfferingResponse>
    getTeacherOfferings(
            @PathVariable Long teacherId) {

        return teacherService
                .getTeacherOfferings(
                        teacherId);
    }
}
