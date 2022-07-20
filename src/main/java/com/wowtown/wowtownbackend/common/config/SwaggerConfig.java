package com.wowtown.wowtownbackend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration	// 스프링 실행시 설정파일 읽어드리기 위한 어노테이션
@EnableSwagger2	// Swagger2를 사용하겠다는 어노테이션
@SuppressWarnings("unchecked")	// warning밑줄 제거를 위한 태그
public class SwaggerConfig extends WebMvcConfigurationSupport {

    //리소스 핸들러 설
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


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
    public Docket AllApi() {
        return getDocket("ALL", Predicates.or(
                PathSelectors.regex("/*.*")));
    }

    //swagger 설정.
    public Docket getDocket(String groupName, Predicate<String> predicate) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wowtown.wowtownbackend"))
                .paths(predicate)
                .apis(RequestHandlerSelectors.any())
                .build();
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
