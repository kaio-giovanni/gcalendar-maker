package com.gcalendar.maker.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class DotEnvUtils {

    private DotEnvUtils () {
    }

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    public static String getDatabaseUrl () {
        return dotenv.get("DB_URL");
    }

    public static String getDatabaseUserName () {
        return dotenv.get("DB_USERNAME");
    }

    public static String getDatabasePassword () {
        return dotenv.get("DB_PASSWORD");
    }

    public static String getGoogleClientSecret () {
        return dotenv.get("GOOGLE_CLIENT_SECRET");
    }

    public static String getGoogleClientId () {
        return dotenv.get("GOOGLE_CLIENT_ID");
    }

    public static String getGoogleApiKey () {
        return dotenv.get("GOOGLE_API_KEY");
    }
}
