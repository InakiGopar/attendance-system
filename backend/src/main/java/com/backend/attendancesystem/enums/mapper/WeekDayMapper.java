package com.backend.attendancesystem.enums.mapper;

import com.backend.attendancesystem.enums.WeekDay;

import java.time.DayOfWeek;

public class WeekDayMapper {

    public static WeekDay convertDayOfWeek(DayOfWeek dayOfWeek) {
        return WeekDay.valueOf(dayOfWeek.name());
    }
}
