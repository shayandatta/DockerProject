package com.spring.DockerProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.spring.DockerProject.config.SwaggerServicesConfig;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties(value = SwaggerServicesConfig.class)
public class DockerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerProjectApplication.class, args);
	}
	
}
