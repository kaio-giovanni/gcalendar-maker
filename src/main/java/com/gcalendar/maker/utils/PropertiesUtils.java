package com.gcalendar.maker.utils;

public class PropertiesUtils {

    private PropertiesUtils () {
        // Do nothing
    }

    public static void loadByDotEnv () {
        System.setProperty("spring.datasource.url", DotEnvUtils.getDatabaseUrl());
        System.setProperty("spring.datasource.username", DotEnvUtils.getDatabaseUserName());
        System.setProperty("spring.datasource.password", DotEnvUtils.getDatabasePassword());
    }
}
