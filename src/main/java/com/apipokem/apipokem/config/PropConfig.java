package com.apipokem.apipokem.config;


import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * clase de configuracion
 */
@Configuration
public class PropConfig {
    public SpringApplicationBuilder configure(SpringApplicationBuilder builder, Class theClass) {
        Properties props = new Properties();

        return builder.properties(props).sources(new Class[] { theClass }).bannerMode(Banner.Mode.OFF);
    }
}
