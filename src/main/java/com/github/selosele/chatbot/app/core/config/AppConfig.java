package com.github.selosele.chatbot.app.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Configuration
@PropertySources({
	@PropertySource("classpath:/env.properties")
})
public class AppConfig implements WebMvcConfigurer {

	@Bean
	public XmlMapper xmlMapper() {
		return new XmlMapper();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
