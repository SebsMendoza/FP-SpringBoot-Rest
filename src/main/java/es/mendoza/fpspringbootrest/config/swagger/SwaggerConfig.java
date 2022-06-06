package es.mendoza.fpspringbootrest.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        return new ApiInfo(
                "FP-Spring Boot API REST - DAM 2021/2022",
                "API REST de un grado superior con Spring Boot. 2021/2022",
                "1.0",
                "Terms of Service",
                new Contact("Jhoan Sebastián Mendoza Acosta", "https://github.com/SebsMendoza",
                        "jhoansebastian.mendoza@alumno.iesluisvives.org"),
                "MIT",
                "https://github.com/SebsMendoza/FP-SpringBoot-Rest/blob/master/LICENSE", new ArrayList<>()
        );
    }
}
