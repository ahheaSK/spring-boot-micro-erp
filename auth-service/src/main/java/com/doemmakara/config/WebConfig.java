package com.doemmakara.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Value("${app.basePath}")
	private String basePath;

	@Override
	public void addCorsMappings(@NonNull CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOriginPatterns("http://localhost:8080", "http://localhost:8081", "http://localhost:4201")
				.allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE") // put the http verbs you want allow
				.allowedHeaders("*") // put the http headers you want allow;
				.exposedHeaders("Authorization", "X-Company-ID") // Expose your custom headers
				.allowCredentials(true);
	}

	@Override
	public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/images/**").addResourceLocations("file:" + basePath);
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/images/");
	}

}

