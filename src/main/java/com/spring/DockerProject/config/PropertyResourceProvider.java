package com.spring.DockerProject.config;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.spring.DockerProject.service.UserServiceDBImpl;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Controller
@Primary
class PropertyResourceProvider implements SwaggerResourcesProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyResourceProvider.class);
	
	@Autowired
	private SwaggerServicesConfig config;
	/**
	 * Build and return list of Swagger Resources (i.e. each microservice api-docs
	 * info)
	 */
	@Override
	public List get() {
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			logger.info(ow.writeValueAsString(config.getServices()));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return config.getServices() != null ? config.getServices().stream().map(svc -> {
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(svc.getName());
			swaggerResource.setLocation(svc.getUrl());
			swaggerResource.setSwaggerVersion(svc.getVersion());
			return swaggerResource;
		}).collect(Collectors.toList()) : Collections.EMPTY_LIST;
	}

}
