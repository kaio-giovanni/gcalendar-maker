package com.gcalendar.maker.controller;

import com.gcalendar.maker.business.google.GoogleCalendarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class GoogleController {

    @Autowired
    private GoogleCalendarServiceImpl googleCalendarService;

    @GetMapping(value = "/google-register")
    public ResponseEntity<String> register () {
        String html = googleCalendarService.getGoogleLoginPage();

        return new ResponseEntity<>(html, HttpStatus.OK);
    }
}
