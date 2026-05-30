package com.booking.serviceImpl;



import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.dto.AddSessionRequest;
import com.booking.dto.CreateOfferingRequest;
import com.booking.dto.OfferingResponse;
import com.booking.dto.SessionDto;
import com.booking.dto.SessionResponse;
import com.booking.entity.Course;
import com.booking.entity.Offering;
import com.booking.entity.Session;
import com.booking.entity.Teacher;
import com.booking.exception.ResourceNotFoundException;
import com.booking.repository.CourseRepository;
import com.booking.repository.OfferingRepository;
import com.booking.repository.SessionRepository;
import com.booking.repository.TeacherRepository;
import com.booking.service.TeacherService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TeacherServiceImpl
        implements TeacherService {

    private final TeacherRepository teacherRepo;
    private final CourseRepository courseRepo;
    private final OfferingRepository offeringRepo;
    private final SessionRepository sessionRepo;

    @Override
    public Long createOffering(
            CreateOfferingRequest request) {

        Teacher teacher =
                teacherRepo.findById(
                        request.getTeacherId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Teacher Not Found"));

        Course course =
                courseRepo.findById(
                        request.getCourseId())
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Course Not Found"));

        Offering offering =
                Offering.builder()
                        .batchName(request.getBatchName())
                        .teacher(teacher)
                        .course(course)
                        .build();

        offeringRepo.save(offering);

        return offering.getId();
    }

    @Override
    public void addSessions(
            Long offeringId,
            AddSessionRequest request) {

        Offering offering =
                offeringRepo.findById(offeringId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Offering Not Found"));

        ZoneId zone =
                ZoneId.of(request.getTimezone());

        for (SessionDto dto :
                request.getSessions()) {

            LocalDateTime startLocal =
                    LocalDateTime.parse(dto.getStart());

            LocalDateTime endLocal =
                    LocalDateTime.parse(dto.getEnd());

            Instant startUtc =
                    startLocal.atZone(zone)
                            .toInstant();

            Instant endUtc =
                    endLocal.atZone(zone)
                            .toInstant();

            Session session =
                    Session.builder()
                            .offering(offering)
                            .startTimeUtc(startUtc)
                            .endTimeUtc(endUtc)
                            .build();

            sessionRepo.save(session);
        }
    }

    @Override
    public List<OfferingResponse> getTeacherOfferings(
            Long teacherId) {

        List<Offering> offerings =
                offeringRepo.findByTeacherId(teacherId);

        return offerings.stream()
                .map(this::mapOffering)
                .toList();
    }

    private OfferingResponse mapOffering(
            Offering offering) {

        List<SessionResponse> sessions =
                offering.getSessions()
                        .stream()
                        .map(session ->
                                SessionResponse.builder()
                                        .sessionId(session.getId())
                                        .startTime(session.getStartTimeUtc().toString())
                                        .endTime(session.getEndTimeUtc().toString())
                                        .build())
                        .toList();

        return OfferingResponse.builder()
                .offeringId(offering.getId())
                .courseName(offering.getCourse().getCourseName())
                .teacherName(offering.getTeacher().getName())
                .batchName(offering.getBatchName())
                .sessions(sessions)
                .build();
    }
}