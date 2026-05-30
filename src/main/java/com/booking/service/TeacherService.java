package com.booking.service;

import java.util.List;

import com.booking.dto.AddSessionRequest;
import com.booking.dto.CreateOfferingRequest;
import com.booking.dto.OfferingResponse;

public interface TeacherService {

    Long createOffering(
            CreateOfferingRequest request);

    void addSessions(
            Long offeringId,
            AddSessionRequest request);

    List<OfferingResponse> getTeacherOfferings(
            Long teacherId);
}
