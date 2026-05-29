CREATE TYPE role_type AS ENUM (
    'EXECUTIVE',
    'TEACHER'
);

CREATE TYPE attendance_status AS ENUM (
    'PRESENT',
    'CONTAGIOUS_DISEASE',
    'NON_CONTAGIOUS_DISEASE',
    'WEATHER_ISSUE',
    'EXCUSED_ABSENCE',
    'ABSENT'
);

CREATE TYPE week_day AS ENUM (
    'MONDAY',
    'TUESDAY',
    'WEDNESDAY',
    'THURSDAY',
    'FRIDAY'
);

CREATE TABLE institution (
                             institution_id UUID PRIMARY KEY,

                             name VARCHAR(255) NOT NULL,

                             created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE app_user (
                          user_id UUID PRIMARY KEY,

                          institution_id UUID NOT NULL,

                          role role_type NOT NULL,

                          name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,

                          email VARCHAR(255) NOT NULL UNIQUE,
                          password VARCHAR(255) NOT NULL,

                          created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_user_institution
                              FOREIGN KEY (institution_id)
                                  REFERENCES institution(institution_id)
);


CREATE TABLE student (
                         student_id UUID PRIMARY KEY,

                         institution_id UUID NOT NULL,

                         name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,

                         birth_date DATE,

                         nationality VARCHAR(100),

                         created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT fk_student_institution
                             FOREIGN KEY (institution_id)
                                 REFERENCES institution(institution_id)
);

CREATE TABLE course (
                        course_id UUID PRIMARY KEY,

                        institution_id UUID NOT NULL,

                        name VARCHAR(100) NOT NULL,

                        created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT fk_course_institution
                            FOREIGN KEY (institution_id)
                                REFERENCES institution(institution_id)
);

CREATE TABLE enrollment (
                            student_id UUID NOT NULL,
                            course_id UUID NOT NULL,

                            created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            PRIMARY KEY (
                                         student_id,
                                         course_id
                                ),

                            CONSTRAINT fk_enrollment_student
                                FOREIGN KEY (student_id)
                                    REFERENCES student(student_id),

                            CONSTRAINT fk_enrollment_course
                                FOREIGN KEY (course_id)
                                    REFERENCES course(course_id)
);

CREATE TABLE schedule (
                          schedule_id UUID PRIMARY KEY,

                          institution_id UUID NOT NULL,
                          course_id UUID NOT NULL,
                          user_id UUID NOT NULL,

                          day week_day NOT NULL,

                          from_time TIME NOT NULL,
                          to_time TIME NOT NULL,

                          created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT fk_schedule_institution
                              FOREIGN KEY (institution_id)
                                  REFERENCES institution(institution_id),

                          CONSTRAINT fk_schedule_course
                              FOREIGN KEY (course_id)
                                  REFERENCES course(course_id),

                          CONSTRAINT fk_schedule_user
                              FOREIGN KEY (user_id)
                                  REFERENCES app_user(user_id),

                          CONSTRAINT uq_schedule
                              UNIQUE (
                                      course_id,
                                      day,
                                      from_time,
                                      to_time
                                  )
);

CREATE TABLE attendance (
                            attendance_id UUID PRIMARY KEY,

                            student_id UUID NOT NULL,
                            course_id UUID NOT NULL,

                            institution_id UUID NOT NULL,

                            user_id UUID NOT NULL,

                            attendance_date DATE NOT NULL,

                            status attendance_status NOT NULL,

                            observations TEXT,

                            created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

                            CONSTRAINT fk_attendance_student
                                FOREIGN KEY (student_id)
                                    REFERENCES student(student_id),

                            CONSTRAINT fk_attendance_course
                                FOREIGN KEY (course_id)
                                    REFERENCES course(course_id),

                            CONSTRAINT fk_attendance_user
                                FOREIGN KEY (user_id)
                                    REFERENCES app_user(user_id),

                            CONSTRAINT fk_attendance_institution
                                FOREIGN KEY (institution_id)
                                    REFERENCES institution(institution_id),

                            CONSTRAINT uq_attendance
                                UNIQUE (
                                        student_id,
                                        course_id,
                                        attendance_date
                                    )
);