package com.example.taskManagementSystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition (servers = {
        @Server(url = "http://${SERVER_IP:localhost}:${SERVER_HTTP_PORT:server.port}${SWAGGER_PREFIX:}", description = "Server")},
        info = @Info(
        title = "Task management system core",
        description = "Made as research project at TPU", version = "1.0.0",
        contact = @Contact(
                name = "Михалёв Максим",
                url = "https://t.me/Handlest"
        )
    )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class OpenApiConfig {

}