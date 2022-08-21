package com.spring.DockerProject.config;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.RoutesDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.model.rest.RestsDefinition;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class CamelConfig extends RouteBuilder implements CamelContextConfiguration{

	@Autowired
	private ApplicationContext applicationContext;	
	
	@Value("${swagger.server.context-path}")
	private String contextPath;
	
	@Value("${server.port}")
	private String serverPort;
	
	private void loadXmlRoutes(CamelContext camelContext, String directory) {
		try {
			Resource[] xmlRoutes = applicationContext.getResources(directory);
			for (final Resource xmlRoute : xmlRoutes) {
				final RoutesDefinition xmlDefination = camelContext.loadRoutesDefinition(xmlRoute.getInputStream());
					camelContext.addRouteDefinitions(xmlDefination.getRoutes());				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadXmlRests(CamelContext camelContext, String directory) {
		try {
			Resource[] xmlRests = applicationContext.getResources(directory);
			for (final Resource xmlRest : xmlRests) {
				final RestsDefinition xmlDefinations = camelContext.loadRestsDefinition(xmlRest.getInputStream());
				camelContext.addRestDefinitions(xmlDefinations.getRests());
				for(final RestDefinition xmlDefination : xmlDefinations.getRests())
				{
					final List<RouteDefinition> routeDefinations = xmlDefination.asRouteDefinition(camelContext);
					camelContext.addRouteDefinitions(routeDefinations);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void beforeApplicationStart(CamelContext camelContext) {
		
		loadXmlRoutes(camelContext,"classpath:camel-routes/*.xml");
		loadXmlRests(camelContext,"classpath:rest-routes/*.xml");		
	}

	@Override
	public void afterApplicationStart(CamelContext camelContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure() throws Exception {
		restConfiguration()
        .component("servlet")
        .scheme("http")
        .host("localhost")
        .bindingMode(RestBindingMode.auto)
        .dataFormatProperty("prettyPrint", "true")
        .port(serverPort)
        // add swagger api-doc out of the box
        .contextPath(contextPath)    // base-path append for swagger docs
        .apiContextPath("/v2/api-docs")
        .apiProperty("api.title", "User API")
        .apiProperty("api.version", "1.2.3")
        // and enable CORS
        .apiProperty("cors", "true");
		
	}
	
}
