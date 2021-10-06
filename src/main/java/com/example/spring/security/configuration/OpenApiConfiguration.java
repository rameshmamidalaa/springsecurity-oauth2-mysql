/**
 * 
 */
package com.example.spring.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * @author Ramesh_Mamidala
 *
 */
@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenApiConfiguration {

	private final BuildProperties buildProperties;

	@Autowired
	public OpenApiConfiguration(BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	@Bean
	public OpenAPI openAPI(@Value("${application-description}") String appDesciption) {
		return new OpenAPI().info(getProjectInformation(appDesciption));

	}

	private Info getProjectInformation(String appDesciption) {
		return new Info().title(buildProperties.getName()).version(buildProperties.getVersion())
				.description(appDesciption).termsOfService("http://swagger.io/terms/")
				.license(new License().name("Apache 2.0").url("http://springdoc.org"));
	}

}
