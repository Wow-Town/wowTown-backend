package com.wowtown.wowtownbackend.common.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration	// 스프링 실행시 설정파일 읽어드리기 위한 어노테이션
@EnableSwagger2	// Swagger2를 사용하겠다는 어노테이션
@SuppressWarnings("unchecked")	// warning밑줄 제거를 위한 태그
public class SwaggerConfig {

    //리소스 핸들러 설
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }


    // API마다 구분짓기 위한 설정.
    @Bean
    public Docket UserApi() {
        return getDocket("USER", Predicates.or(
                PathSelectors.regex("/users.*")));
    }


    @Bean
    public Docket AvatarApi() {
        return getDocket("AVATAR", Predicates.or(
                PathSelectors.regex("/avatars.*")));
    }


    @Bean
    public Docket ChannelApi() {
        return getDocket("CHANNEL", Predicates.or(
                PathSelectors.regex("/channels.*")));

    }
    @Bean
    public Docket MultiChatRoomApi() {
        return getDocket("MULTICHATROOM", Predicates.or(
                PathSelectors.regex("/multiChatRooms.*")));
    }
    @Bean
    public Docket StudyGroupApi() {
        return getDocket("STUDYGROUP", Predicates.or(
                PathSelectors.regex("/studyGroups.*")));
    }
    @Bean
    public Docket FriendApi() {
        return getDocket("FRIEND", Predicates.or(
                PathSelectors.regex("/firends.*")));
    }
    @Bean
    public Docket AllApi() {
        return getDocket("ALL", Predicates.or(
                PathSelectors.regex("/*.*")));
    }

    //swagger 설정.
    public Docket getDocket(String groupName, Predicate<String> predicate) {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wowtown.wowtownbackend"))
                .paths(predicate)
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(metaData())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(userApiKey()));
//                .securitySchemes(Arrays.asList(avatarApiKey()));
    }
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("CherishPet REST API")
                .description("CherishPet rest api documentation")
                .license("Apache License Version 2.0")
                .build();
    }

    private ApiKey userApiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }
//    private ApiKey avatarApiKey() {
//        return new ApiKey("AvatarJWT", "avatarId", "header");
//    }

    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
    //swagger ui 설정.
    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .validatorUrl("")
                .build();
    }


}
