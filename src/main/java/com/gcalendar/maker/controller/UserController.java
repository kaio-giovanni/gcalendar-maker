package com.gcalendar.maker.controller;

import com.gcalendar.maker.business.user.UserServiceImpl;
import com.gcalendar.maker.dto.UserDto;
import com.gcalendar.maker.dto.UserSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping({"", ""})
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserSaveDto newUser) {
        UserDto dto = new UserDto(userService.save(newUser));

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/calendar")
    public ResponseEntity<File> getGoogleCalendarByUser(Long userId, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        File file = userService.makeGoogleCalendar(userId, startDate, endDate);

        return new ResponseEntity<>(file, HttpStatus.OK);
    }
}
