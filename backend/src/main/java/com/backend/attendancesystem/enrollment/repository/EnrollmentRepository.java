package com.backend.attendancesystem.enrollment.repository;

import com.backend.attendancesystem.enrollment.model.EnrollmentEntity;
import com.backend.attendancesystem.enrollment.model.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, EnrollmentId> {
}
