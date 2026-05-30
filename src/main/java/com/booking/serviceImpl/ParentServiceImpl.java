package com.booking.serviceImpl;


import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.booking.dto.BookOfferingRequest;
import com.booking.dto.OfferingResponse;
import com.booking.dto.SessionResponse;
import com.booking.entity.Booking;
import com.booking.entity.Offering;
import com.booking.entity.Parent;
import com.booking.entity.Session;
import com.booking.exception.ConflictException;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.BookingRepository;
import com.booking.repository.OfferingRepository;
import com.booking.repository.ParentRepository;
import com.booking.repository.SessionRepository;
import com.booking.service.ParentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParentServiceImpl
        implements ParentService {

    private final ParentRepository parentRepo;
    private final OfferingRepository offeringRepo;
    private final SessionRepository sessionRepo;
    private final BookingRepository bookingRepo;
    
    @Override
    public List<OfferingResponse> getAvailableOfferings(Long parentId) {

        Parent parent = parentRepo.findById(parentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Parent Not Found"));

        ZoneId parentZone = ZoneId.of(parent.getTimezone());

        List<Offering> offerings = offeringRepo.findAll();

        return offerings.stream()
                .map(offering -> {

                    List<SessionResponse> sessions =
                            offering.getSessions()
                                    .stream()
                                    .map(session ->
                                            SessionResponse.builder()
                                                    .sessionId(session.getId())
                                                    .startTime(session.getStartTimeUtc()
                                                            .atZone(parentZone)
                                                            .toString())
                                                    .endTime(session.getEndTimeUtc()
                                                            .atZone(parentZone)
                                                            .toString())
                                                    .build())
                                    .collect(Collectors.toList());

                    return OfferingResponse.builder()
                            .offeringId(offering.getId())
                            .courseName(offering.getCourse().getCourseName())
                            .teacherName(offering.getTeacher().getName())
                            .batchName(offering.getBatchName())
                            .sessions(sessions)
                            .build();
                })
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public void bookOffering(
            BookOfferingRequest request) {

        Parent parent =
                parentRepo.lockParent(
                        request.getParentId());

        if (parent == null) {

            throw new ResourceNotFoundException(
                    "Parent Not Found");
        }

        Offering offering =
                offeringRepo.findById(
                        request.getOfferingId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Offering Not Found"));

        boolean alreadyBooked =
                bookingRepo.existsByParentIdAndOfferingId(
                        request.getParentId(),
                        request.getOfferingId());

        if (alreadyBooked) {

            throw new ConflictException(
                    "Offering already booked");
        }

        validateSessionConflict(
                parent.getId(),
                offering.getId());

        Booking booking =
                Booking.builder()
                        .parent(parent)
                        .offering(offering)
                        .bookedAt(
                                java.time.LocalDateTime.now())
                        .build();

        bookingRepo.save(booking);
    }
    
    private void validateSessionConflict(
            Long parentId,
            Long offeringId) {

        List<Session> existingSessions =
                bookingRepo.findBookedSessions(parentId);

        List<Session> newSessions =
                sessionRepo.findByOfferingId(offeringId);

        for (Session existing :
                existingSessions) {

            for (Session incoming :
                    newSessions) {

                boolean overlap =
                        incoming.getStartTimeUtc()
                                .isBefore(
                                        existing.getEndTimeUtc())
                                &&
                        incoming.getEndTimeUtc()
                                .isAfter(
                                        existing.getStartTimeUtc());

                if (overlap) {

                    throw new ConflictException(
                            "Session timing conflict found");
                }
            }
        }
    }
    
    @Override
    public List<OfferingResponse> getBookings(
            Long parentId) {

        Parent parent =
                parentRepo.findById(parentId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Parent Not Found"));

        ZoneId zone =
                ZoneId.of(parent.getTimezone());

        return bookingRepo.findByParentId(parentId)
                .stream()
                .map(booking -> {

                    Offering offering =
                            booking.getOffering();

                    List<SessionResponse> sessions =
                            offering.getSessions()
                                    .stream()
                                    .map(session ->
                                            SessionResponse.builder()
                                                    .sessionId(session.getId())
                                                    .startTime(
                                                            session.getStartTimeUtc()
                                                                    .atZone(zone)
                                                                    .toString())
                                                    .endTime(
                                                            session.getEndTimeUtc()
                                                                    .atZone(zone)
                                                                    .toString())
                                                    .build())
                                    .collect(Collectors.toList());;

                    return OfferingResponse.builder()
                            .offeringId(offering.getId())
                            .courseName(
                                    offering.getCourse().getCourseName())
                            .teacherName(
                                    offering.getTeacher().getName())
                            .batchName(
                                    offering.getBatchName())
                            .sessions(sessions)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
