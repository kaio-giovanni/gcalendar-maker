package com.gcalendar.maker.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class Logger {

    public void error (Throwable throwable, Class clazz) {
        Log log = LogFactory.getLog(clazz);
        log.error(throwable.getMessage());
    }
}
