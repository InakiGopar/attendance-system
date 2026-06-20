package com.backend.attendancesystem.schedule.repository;

import com.backend.attendancesystem.enums.WeekDay;
import com.backend.attendancesystem.schedule.model.ScheduleEntity;
import com.backend.attendancesystem.user.dto.response.UserCourseResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {

    @Query("""
    SELECT new com.backend.attendancesystem.user.dto.response.UserCourseResponse(
        c.courseId,
        c.name,
        s.fromTime,
        s.toTime
    )
    FROM ScheduleEntity s
        JOIN s.course c
    WHERE s.user.userId = :userId
      AND s.day = :day
    ORDER BY s.fromTime
""")
    List<UserCourseResponse> findCoursesByUserAndWeekDay(
            UUID userId,
            WeekDay day
    );
}
