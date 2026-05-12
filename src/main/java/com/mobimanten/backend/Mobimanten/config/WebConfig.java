package com.mobimanten.backend.Mobimanten.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Hemos movido la configuración de CORS a SecurityConfig para evitar conflictos
}