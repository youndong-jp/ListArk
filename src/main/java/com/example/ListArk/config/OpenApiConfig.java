package com.example.ListArk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ListArk API")
                        .version("1.0")
                        .description("Lost Ark 캐릭터 정보 조회 API - 로스트아크 공식 API를 프론트엔드 친화적인 형태로 변환")
                        .contact(new Contact()
                                .name("ListArk Team")
                                .url("https://github.com/youndong-jp/ListArk")
                        )
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server")
                ));
    }
}