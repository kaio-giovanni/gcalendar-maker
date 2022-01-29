package com.gcalendar.maker.dto;

import com.gcalendar.maker.utils.DateTimeUtils;

import java.time.LocalDateTime;

public class PeriodicityDto {

    private final DateTimeUtils.Periodicity periodicity;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    public PeriodicityDto (DateTimeUtils.Periodicity periodicity, LocalDateTime startDate, LocalDateTime endDate) {
        this.periodicity = periodicity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateTimeUtils.Periodicity getPeriodicity () {
        return periodicity;
    }

    public LocalDateTime getStartDate () {
        return startDate;
    }

    public LocalDateTime getEndDate () {
        return endDate;
    }
}
