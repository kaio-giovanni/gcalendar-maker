package com.gcalendar.maker.dto;

import javax.validation.constraints.NotEmpty;

public class UserSaveDto {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String email;

    @NotEmpty
    private final String accessToken;

    public UserSaveDto (String name, String email, String accessToken) {
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
    }

    public String getName () {
        return name;
    }

    public String getEmail () {
        return email;
    }

    public String getAccessToken () {
        return accessToken;
    }
}
