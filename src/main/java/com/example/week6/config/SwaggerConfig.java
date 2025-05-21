package com.example.week6.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LikeLion")                         // 서비스 이름
                        .version("v1")                                 // 버전
                        .description("MakeProject API TEST")          // 설명
                        .termsOfService("http://localhost:8080/")     // 서비스 URL
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Board")
                                .url("http://your-license-url.com"))
                );
    }
}

