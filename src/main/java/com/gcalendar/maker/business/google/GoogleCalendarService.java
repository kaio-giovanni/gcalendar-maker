package com.gcalendar.maker.business.google;

import com.gcalendar.maker.dto.GoogleCalendarDto;
import com.gcalendar.maker.entity.User;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.auth.http.HttpTransportFactory;
import com.google.auth.oauth2.OAuth2Credentials;

import java.io.IOException;
import java.util.List;

interface GoogleCalendarService {

    OAuth2Credentials getGoogleCredentials(User user, HttpTransportFactory httpTransport) throws IOException;

    Calendar getGoogleService(User user) throws IOException;

    List<GoogleCalendarDto> getCalendarEvents(User user, DateTime timeMin, DateTime timeMax) throws IOException;
}
