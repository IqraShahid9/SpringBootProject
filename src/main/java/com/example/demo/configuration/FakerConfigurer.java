package com.example.demo.configuration;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FakerConfigurer {
    @Bean
    Faker faker(){
        return new Faker();
    }
}
