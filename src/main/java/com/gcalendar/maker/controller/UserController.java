package com.gcalendar.maker.controller;

import com.gcalendar.maker.business.user.UserServiceImpl;
import com.gcalendar.maker.dto.UserDto;
import com.gcalendar.maker.dto.UserSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping({"", ""})
    public ResponseEntity<UserDto> save (@Valid @RequestBody UserSaveDto newUser) {
        UserDto dto = new UserDto(userService.save(newUser));

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<UserDto>> findAll () {
        List<UserDto> dtos = userService.findAll()
                .stream()
                .map(UserDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/calendar", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getGoogleCalendarByUser (@RequestParam(name = "userId") Long userId,
                                                           @RequestParam(name = "startDate")
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                           @RequestParam(name = "endDate")
                                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) throws IOException {
        File file = userService.makeGoogleCalendar(userId, startDate, endDate);
        byte[] fileContent = Files.readAllBytes(file.toPath());

        return new ResponseEntity<>(fileContent, HttpStatus.OK);
    }
}
