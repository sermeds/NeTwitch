package com.example.netwitch.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Comics Shop Api",
                description = "Comics Shop",
                version = "1.0.0",
                contact = @Contact(
                        name = "Arkhipov Sergey",
                        email = "mytopemail@genius.com",
                        url = "https://top.creator.genius.dev"
                )
        )
)

public class OpenApiConfig {
}
