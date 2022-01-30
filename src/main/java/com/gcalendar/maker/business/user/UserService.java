package com.gcalendar.maker.business.user;

import com.gcalendar.maker.dto.UserSaveDto;
import com.gcalendar.maker.entity.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

interface UserService {

    User save(UserSaveDto newUser);

    User findById (Long id);

    List<User> findAll();

    File makeGoogleCalendar (Long id, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
}
