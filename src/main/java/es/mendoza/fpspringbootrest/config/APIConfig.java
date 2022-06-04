package es.mendoza.fpspringbootrest.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
public class APIConfig {
    @Value("${api.path}.path")
    public static final String API_PATH = "/rest";
    @Value("${api.version}")
    public static final String API_VERSION = "1.0";
}
