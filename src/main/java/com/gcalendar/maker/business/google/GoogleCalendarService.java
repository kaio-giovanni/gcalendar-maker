package com.gcalendar.maker.business.google;

import com.gcalendar.maker.dto.GoogleCalendarDto;
import com.gcalendar.maker.entity.User;
import com.google.api.client.util.DateTime;

import java.io.IOException;
import java.util.List;

interface GoogleCalendarService {

    List<GoogleCalendarDto> getCalendarEvents(User user, DateTime timeMin, DateTime timeMax) throws IOException;
}
