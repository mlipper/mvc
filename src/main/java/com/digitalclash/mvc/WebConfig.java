package com.digitalclash.mvc;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// See https://www.baeldung.com/spring-mvc-content-negotiation-json-xml

// Spring Boot configures common Spring MVC defaults described in the
// reference docs section "Spring MVC Auto-configuration". This happens
// when a class has the @Configuration annotation and implements
// WebMvcConfigurer.
// To prevent Boot from configuring any defaults, add @EnableWebMvc.
// NOTE: A Spring MVC application can only have one class with this annotation.
// @EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @SuppressWarnings("deprecation")
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        logger.info("Configuring content negotiation with instance {}.", configurer);
        configurer.favorPathExtension(true).
        favorParameter(true).
        parameterName("format").
        ignoreAcceptHeader(true).
        useJaf(false).
        defaultContentType(MediaType.APPLICATION_JSON).
        mediaType("xml", MediaType.APPLICATION_XML).
        mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
                logger.info("Path info: {}", request.getPathInfo());
                logger.info("Path translated: {}", request.getPathTranslated());
                logger.info("Handler: {} instance of {}", handler, handler.getClass().getCanonicalName());
                return true;
            }
 
        });
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        logger.info("PathMatchConfigurer before modification:");
        //configurer.
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .modulesToInstall(new ParameterNamesModule());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).propertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE).build()));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**", "/favicon.ico")
                .addResourceLocations("/public", "classpath:/static/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)));
    }
}
