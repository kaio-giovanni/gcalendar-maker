package com.gcalendar.maker.utils;

import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateUtils {

    private TemplateUtils () {
        // do nothing
    }

    public static SpringTemplateEngine getTemplateEngine () {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setForceTemplateMode(true);
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }
}
