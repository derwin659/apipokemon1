package com.apipokem.apipokem;

import com.apipokem.apipokem.config.PropConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.function.BiConsumer;

@SpringBootApplication
public class ApipokemApplication extends SpringBootServletInitializer implements WebApplicationInitializer{
	static PropConfig configuration = new PropConfig();

	static BiConsumer<Class<?>, String[]> runApplication = SpringApplication::run;
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return configuration.configure(builder, ApipokemApplication.class);
	}
	public static void main(String[] args) {
		runApplication.accept(ApipokemApplication.class,args);
	}


}
