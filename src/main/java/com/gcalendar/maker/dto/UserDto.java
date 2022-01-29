package com.gcalendar.maker.dto;

import com.gcalendar.maker.entity.User;

public class UserDto {

    private final Long id;

    private final String name;

    private final String email;

    public UserDto (User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId () {
        return id;
    }

    public String getName () {
        return name;
    }

    public String getEmail () {
        return email;
    }
}
