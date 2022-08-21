package com.spring.DockerProject.config;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.Data;

@Data
@Primary
@Configuration
@ConfigurationProperties(prefix="documentation.swagger")
public class SwaggerServicesConfig {
	
    List<SwaggerServices> services;
	
    @Data
	public static class SwaggerServices{
        private String name;
        private String url;
        private String version;

    }
	
	
}