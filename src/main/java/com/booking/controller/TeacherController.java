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
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Tag(name = "Teacher APIs", description = "Teacher Management APIs")
public class TeacherController {

    private final TeacherService teacherService;

    @Operation(summary = "Create Offering")
    @PostMapping("/offerings")
    public Long createOffering(
            @RequestBody CreateOfferingRequest request) {

        return teacherService.createOffering(request);
    }

    @Operation(summary = "Add Sessions")
    @PostMapping("/offerings/{offeringId}/sessions")
    public String addSessions(
            @PathVariable Long offeringId,
            @RequestBody AddSessionRequest request) {

        teacherService.addSessions(offeringId, request);

        return "Sessions Added Successfully";
    }

    @Operation(summary = "Get Teacher Offerings")
    @GetMapping("/{teacherId}/offerings")
    public List<OfferingResponse> getTeacherOfferings(
            @PathVariable Long teacherId) {

        return teacherService.getTeacherOfferings(teacherId);
    }
}