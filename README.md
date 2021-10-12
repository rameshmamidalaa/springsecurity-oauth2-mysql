# springsecurity-oauth2-mysql
**Demo project for Spring Boot + SpringSecurity + OAuth2 + MySQL**

### _Technical Stack used in this project:_
	1. spring-boot-starter-parent 2.5.3
	2. Java 1.8
	3. spring-boot-starter-security: Spring Security is the primary choice for implementing application-level security in Spring applications. Generally, its purpose is to offer you a highly customizable way of implementing authentication, authorization, and protection against common attacks.
	4. spring-security-oauth2 2.3.8.RELEASE: OAuth2 is an authorization framework that enables the pplication Web Security to access the resources from the client. To build an OAuth2 application, we need to focus on the Grant Type (Authorization code), Client ID and Client secret. Note that, spring-security-oauth2 2.4.0.RELEASE project is completely deprecated, for more details please have a look on the following links: https://projects.spring.io/spring-security-oauth/docs/oauth2.html &  https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide
	5. spring-security-oauth2-autoconfigure 2.2.3.RELEASE: As spring-security-oauth2 on the project's classpath, so we can take advantage of some auto-configuration to simplify setting up Authorization and Resource Servers.
	6. spring-security-jwt 1.1.0.RELEASE: JSON Web Token (JWT) is a standard for encoding information that may be securely transmitted as a JSON object.
	7. spring-boot-starter-data-jpa:Spring Boot provides spring-boot-starter-data-jpa dependency to connect Spring application with relational database efficiently. The spring-boot-starter-data-jpa internally uses the spring-boot-jpa dependency (since Spring Boot version 1.5.3).
	8. spring-boot-starter-actuator: Actuator is mainly used to expose operational information about the running application — health, metrics, info, dump, env, etc. It uses HTTP endpoints or JMX beans to enable us to interact with it. Once this dependency is on the classpath, several endpoints are available for us out of the box. https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html
	9. mysql-connector-java
	10. lombok: Lombok is used to reduce boilerplate code for model/data objects, Example: it can generate getters and setters for those object automatically by using Lombok annotations. The easiest way is to use the @Data annotation.
	11. springdoc-openapi-ui 1.5.10: springdoc-openapi java library helps automating the generation of API documentation using spring boot projects. springdoc-openapi works by examining an application at runtime to infer API semantics based on spring 	configurations, class structure and various annotations.
	
