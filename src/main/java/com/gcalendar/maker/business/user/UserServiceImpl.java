package com.gcalendar.maker.business.user;

import com.gcalendar.maker.business.google.GoogleCalendarServiceImpl;
import com.gcalendar.maker.dto.GoogleCalendarDto;
import com.gcalendar.maker.dto.PeriodicityDto;
import com.gcalendar.maker.dto.UserSaveDto;
import com.gcalendar.maker.entity.User;
import com.gcalendar.maker.exception.UserNotFoundException;
import com.gcalendar.maker.utils.CustomTagWorkerFactory;
import com.gcalendar.maker.utils.DateTimeUtils;
import com.gcalendar.maker.utils.TemplateUtils;
import com.google.api.client.util.DateTime;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserServiceImpl implements UserService {

    private static final String EVENTS = "events";

    @Autowired
    private UserRepository repository;

    @Autowired
    private GoogleCalendarServiceImpl googleCalendarService;

    @Override
    public User save (UserSaveDto newUser) {
        return repository.save(new User(newUser.getName(), newUser.getEmail(), newUser.getAccessToken()));
    }

    @Override
    public User findById (Long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UserNotFoundException(String.format("User id %s not found", id));
    }

    @Override
    public List<User> findAll () {
        return repository.findAll();
    }

    @Override
    public File makeGoogleCalendar (Long userId, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        User user = findById(userId);
        Date sDate = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date eDate = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
        List<GoogleCalendarDto> events = googleCalendarService.getCalendarEvents(user, new DateTime(sDate), new DateTime(eDate));
        DateTimeUtils.Periodicity periodicity = DateTimeUtils.getPeriodicity(startDate, endDate);
        PeriodicityDto periodicityDto = new PeriodicityDto(periodicity, startDate, endDate);
        String html = getTemplateByPeriodicity(events, periodicityDto);
        String fileName = String.format("Google_Calendar_%s_UserId%s_%s", periodicity, userId, System.currentTimeMillis());
        File file = File.createTempFile(fileName, ".pdf");
        ConverterProperties props = new ConverterProperties();
        props.setTagWorkerFactory(new CustomTagWorkerFactory());
        HtmlConverter.convertToPdf(html, new FileOutputStream(file), props);
        return file;
    }

    private String getTemplateByPeriodicity (List<GoogleCalendarDto> events, PeriodicityDto periodicityDto) {
        switch (periodicityDto.getPeriodicity()) {
            case DAILY:
                return getDailyCalendarTemplate(events, periodicityDto.getStartDate().toLocalDate());
            case WEEKLY:
                return getWeeklyCalendarTemplate(events, periodicityDto.getStartDate().toLocalDate());
            default:
                return getMonthlyCalendarTemplate(events, periodicityDto.getStartDate().toLocalDate());
        }
    }

    private String getDailyCalendarTemplate (List<GoogleCalendarDto> events, LocalDate localDate) {
        Map<String, List<GoogleCalendarDto>> groupedCalendarEvents = googleCalendarService.groupCalendarDtoByStartDate(events);
        Map<String, List<GoogleCalendarDto>> sortedCalendarEvents = new TreeMap<>(groupedCalendarEvents);

        SpringTemplateEngine templateEngine = TemplateUtils.getTemplateEngine();
        String currentDate = String.format("%s %s %s", localDate.getDayOfMonth(),
                DateTimeUtils.getMonthName(localDate), localDate.getYear());
        Context context = new Context();
        context.setVariable("date", currentDate);
        context.setVariable(EVENTS, sortedCalendarEvents);

        return templateEngine.process("DailyCalendar.html", context);
    }

    private String getWeeklyCalendarTemplate (List<GoogleCalendarDto> events, LocalDate localDate) {
        Map<String, List<GoogleCalendarDto>> groupedCalendarEvents = googleCalendarService.groupCalendarDtoByStartDate(events);
        Map<String, List<GoogleCalendarDto>> sortedCalendarEvents = new TreeMap<>(groupedCalendarEvents);

        SpringTemplateEngine templateEngine = TemplateUtils.getTemplateEngine();
        Context context = new Context();
        context.setVariable("dateInterval", String.format("%s - %s %s %s",
                localDate.getDayOfMonth(),
                localDate.plusDays(6).getDayOfMonth(),
                DateTimeUtils.getMonthName(localDate),
                localDate.getYear()));
        context.setVariable("day_1", localDate.toString());
        context.setVariable("day_2", localDate.plusDays(1).toString());
        context.setVariable("day_3", localDate.plusDays(2).toString());
        context.setVariable("day_4", localDate.plusDays(3).toString());
        context.setVariable("day_5", localDate.plusDays(4).toString());
        context.setVariable("day_6", localDate.plusDays(5).toString());
        context.setVariable("day_7", localDate.plusDays(6).toString());
        context.setVariable(EVENTS, sortedCalendarEvents);

        return templateEngine.process("WeeklyCalendar.html", context);
    }

    private String getMonthlyCalendarTemplate (List<GoogleCalendarDto> events, LocalDate date) {
        Map<String, List<GoogleCalendarDto>> groupedCalendarEvents = googleCalendarService.groupCalendarDtoByStartDate(events);
        int year = date.getYear();
        int monthNumber = date.getMonthValue() - 1;
        int numberOfWeeks = DateTimeUtils.getNumberOfWeeks(year, monthNumber);
        String monthName = DateTimeUtils.getMonthName(date);
        List<Integer> weeklyList = IntStream.rangeClosed(1, numberOfWeeks)
                .boxed().collect(Collectors.toList());

        SpringTemplateEngine templateEngine = TemplateUtils.getTemplateEngine();
        Context context = new Context();
        context.setVariable("year", year);
        context.setVariable("month", monthNumber);
        context.setVariable("yearMonth", year + " " + monthName);
        context.setVariable("weeklyList", weeklyList);
        context.setVariable(EVENTS, groupedCalendarEvents);

        return templateEngine.process("MonthlyCalendar.html", context);
    }
}
