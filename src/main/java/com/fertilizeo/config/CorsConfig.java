package com.fertilizeo.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class CorsConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/compte/add/users")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("POST")
                    .allowCredentials(true);
        }
    }

