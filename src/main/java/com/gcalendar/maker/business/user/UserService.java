package com.gcalendar.maker.business.user;

import com.gcalendar.maker.dto.UserSaveDto;
import com.gcalendar.maker.entity.User;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

interface UserService {

    User save(UserSaveDto newUser);

    User findById (Long id);

    File makeGoogleCalendar (Long id, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
}
