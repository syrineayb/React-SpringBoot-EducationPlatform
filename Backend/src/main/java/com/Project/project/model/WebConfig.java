package com.Project.project.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class WebConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        // Set the maximum allowed file size
        multipartResolver.setMaxUploadSize(52428800); // 50MB
        return multipartResolver;
    }

    public WebConfig() {
    }
// Add other configuration or beans if needed
}
