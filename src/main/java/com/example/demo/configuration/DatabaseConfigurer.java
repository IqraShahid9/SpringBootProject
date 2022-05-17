package com.example.demo.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationPropertiesScan
@ConstructorBinding
@ConfigurationProperties("spring.datasource")
public class DatabaseConfigurer {
    private String url;
    private String username;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Profile("dev")
    @Bean
    public String devDBConnection(){
        System.out.println("url..."+url);
        System.out.println("username..."+username);
        return "DB connection for dev";
    }

    @Profile("prod")
    @Bean
    public String prodDBConnection(){
        System.out.println("url..."+url);
        System.out.println("username..."+username);
        return "DB connection for prod";
    }
}
