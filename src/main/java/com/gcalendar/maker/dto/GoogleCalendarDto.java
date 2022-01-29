package com.gcalendar.maker.dto;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GoogleCalendarDto {
    private final String id;
    private final String location;
    private final String description;
    private final String summary;
    private final DateTime startDateTime;
    private final DateTime endDateTime;
    private final String displayName;
    private final String creatorEmail;
    private final String htmlLink;

    public GoogleCalendarDto (Event event) {
        this.id = event.getId();
        this.location = event.getLocation();
        this.description = event.getDescription();
        this.summary = event.getSummary();
        this.startDateTime = formatStartDate(event);
        this.endDateTime = formatEndDate(event);
        this.displayName = event.getCreator().getDisplayName();
        this.creatorEmail = event.getCreator().getEmail();
        this.htmlLink = event.getHtmlLink();
    }

    public String getId () {
        return id;
    }

    public String getLocation () {
        return location;
    }

    public String getDescription () {
        return description;
    }

    public String getSummary () {
        return summary;
    }

    public DateTime getStartDateTime () {
        return startDateTime;
    }

    public DateTime getEndDateTime () {
        return endDateTime;
    }

    public String getDisplayName () {
        return displayName;
    }

    public String getCreatorEmail () {
        return creatorEmail;
    }

    public String getHtmlLink () {
        return htmlLink;
    }

    private LocalDateTime getLocalDateTime (DateTime dateTime) {
        return LocalDateTime.parse(dateTime.toStringRfc3339(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
    }

    public String getStartDate () {
        LocalDateTime dateTime = getLocalDateTime(startDateTime);
        return dateTime.toLocalDate().toString();
    }

    public DateTime formatStartDate (Event event) {
        EventDateTime startEvent = event.getStart();
        if (startEvent.getDateTime() == null) {
            String date = startEvent.getDate().toString();
            String dateTime = String.format("%sT00:00:00.000-03:00", date);
            return new DateTime(dateTime);
        }
        return startEvent.getDateTime();
    }

    public DateTime formatEndDate (Event event) {
        EventDateTime endEvent = event.getEnd();
        if (endEvent.getDateTime() == null) {
            String date = endEvent.getDate().toString();
            String dateTime = String.format("%sT23:59:59.999-03:00", date);
            return new DateTime(dateTime);
        }
        return endEvent.getDateTime();
    }

    @Override
    public String toString () {
        return "GoogleCalendarDto{" +
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", summary='" + summary + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", displayName='" + displayName + '\'' +
                ", creatorEmail='" + creatorEmail + '\'' +
                ", htmlLink='" + htmlLink + '\'' +
                '}';
    }
}
