package com.gcalendar.maker;

import com.gcalendar.maker.utils.PropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GcalendarMakerApplication {

	public static void main(String[] args) {
		PropertiesUtils.loadByDotEnv();
		SpringApplication.run(GcalendarMakerApplication.class, args);
	}

}
