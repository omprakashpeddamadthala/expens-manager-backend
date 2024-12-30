package com.expens.manager.config;

import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * This is the configuration class for CORS
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-12-29
 * */
@Configuration
public class CrosConfig {

    /**
     *  This method is used to configure the CORS filter
     *  It is used to allow the requests from the specified origin
     *  It is used to allow all the methods and headers
     */
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source =new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowedOriginPatterns( List.of("http://localhost:5173"));
        source.registerCorsConfiguration("/**",configuration);
        return new CorsFilter(source);
    }
}
