package com.booking.service;

import java.util.List;

import com.booking.dto.BookOfferingRequest;
import com.booking.dto.OfferingResponse;

public interface ParentService {

    List<OfferingResponse> getAvailableOfferings(
            Long parentId);

    void bookOffering(
            BookOfferingRequest request);

    List<OfferingResponse> getBookings(
            Long parentId);
}