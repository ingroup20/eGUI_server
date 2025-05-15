package com.ingroup.invoice_web.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    @Bean
    public OpenAPI mallTinyOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Invoice Web 接口文檔")
                        .description("前後端接口內容")
                        .version("v1.0.0")
                        .license(new License().name("Invoice Web 1.0").url("https://github.com/")))
                .externalDocs(new ExternalDocumentation()
                        .description("發票管理系統")
                        .url("http://www.ingroup.com.tw"));
//                //以下是针对SpringSecurity的设置，同时还有设置白名单
//                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
//                .components(new Components()
//                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
//                                new SecurityScheme()
//                                        .name(SECURITY_SCHEME_NAME)
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
    }



    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/**")
                .build();
    }

    //可以创建不同的GroupedOpenApi来判断不同的controller

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("portal")
                .pathsToMatch("/api/**")
                .build();
    }
}
