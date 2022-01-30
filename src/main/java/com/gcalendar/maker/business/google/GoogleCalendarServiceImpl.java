package com.gcalendar.maker.business.google;

import com.gcalendar.maker.dto.GoogleCalendarDto;
import com.gcalendar.maker.entity.User;
import com.gcalendar.maker.utils.DotEnvUtils;
import com.gcalendar.maker.utils.TemplateUtils;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.http.HttpTransportFactory;
import com.google.auth.oauth2.OAuth2Credentials;
import com.google.auth.oauth2.UserCredentials;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    private final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

    private OAuth2Credentials getGoogleCredentials (User user, HttpTransportFactory httpTransport) throws IOException {
        String refreshToken = user.getGoogleAccessToken();
        OAuth2Credentials credentials =
                UserCredentials.newBuilder()
                        .setHttpTransportFactory(httpTransport)
                        .setClientId(DotEnvUtils.getGoogleClientId())
                        .setClientSecret(DotEnvUtils.getGoogleClientSecret())
                        .setRefreshToken(refreshToken)
                        .build();

        credentials.refreshIfExpired();
        return credentials;
    }

    private Calendar getGoogleService (User user) throws IOException {
        HttpTransportFactory httpTransport = new DefaultHttpTransportFactory();
        OAuth2Credentials credentials = getGoogleCredentials(user, httpTransport);

        String applicationName = "Application name";
        return new Calendar.Builder(httpTransport.create(), jacksonFactory, new HttpCredentialsAdapter(credentials))
                .setApplicationName(applicationName)
                .build();
    }

    @Override
    public List<GoogleCalendarDto> getCalendarEvents (User user, DateTime timeMin, DateTime timeMax) throws IOException {
        Calendar calendarService = getGoogleService(user);

        String calendarId = "primary";
        String pageToken = null;
        List<GoogleCalendarDto> calendarDTOS = new ArrayList<>();
        do {
            Events events = calendarService.events()
                    .list(calendarId)
                    .setTimeMax(timeMax)
                    .setTimeMin(timeMin)
                    .setPageToken(pageToken)
                    .execute();
            List<Event> items = events.getItems();

            for (Event event : items) {
                calendarDTOS.add(new GoogleCalendarDto(event));
            }
            pageToken = events.getNextPageToken();
        } while (pageToken != null);

        return calendarDTOS;
    }

    public Map<String, List<GoogleCalendarDto>> groupCalendarDtoByStartDate (List<GoogleCalendarDto> calendarDtos) {
        return calendarDtos
                .stream()
                .collect(Collectors.groupingBy(GoogleCalendarDto::getStartDate));
    }

    public String getGoogleLoginPage () {
        SpringTemplateEngine templateEngine = TemplateUtils.getTemplateEngine();
        Context context = new Context();
        return templateEngine.process("GoogleLogin.html", context);
    }

    private static class DefaultHttpTransportFactory implements HttpTransportFactory {

        @Override
        public HttpTransport create () {
            try {
                return GoogleNetHttpTransport.newTrustedTransport();
            } catch (GeneralSecurityException | IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
