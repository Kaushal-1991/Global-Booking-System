package com.booking.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Global Booking System API")
                                .version("1.0")
                                .description("Teacher & Parent Booking APIs")
                                .contact(
                                        new Contact()
                                                .name("Kaushal Raj Singh")
                                                .email("kaushal@gmail.com")
                                )
                );
    }
}