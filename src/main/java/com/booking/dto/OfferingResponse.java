package com.booking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferingResponse {

    private Long offeringId;
    private String courseName;
    private String teacherName;
    private String batchName;
    private List<SessionResponse> sessions;
}