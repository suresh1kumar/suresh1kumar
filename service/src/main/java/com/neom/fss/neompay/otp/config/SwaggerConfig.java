package com.neom.fss.neompay.otp.config;


import com.neom.fss.neompay.otp.constants.ApiHeaderConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket produceApi() {
        return new Docket(DocumentationType.OAS_30)
            .useDefaultResponseMessages(false)
            .apiInfo(new ApiInfoBuilder()
                .title("NEOM PAY OTP API")
                .description("NEOM PAY OTP Service")
                .version("1.0")
                .build())
               .globalRequestParameters(getGlobalParameterList())
            .tags(new Tag(ServiceTags.OTP_SERVICE, "Otp service"))
            .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .build();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ServiceTags {
        public static final String OTP_SERVICE = "OTP Service";

    }

    private List<RequestParameter> getGlobalParameterList() {
        return Stream.concat(
                        ApiHeaderConstants.getOptionalApiHeaders().stream()
                                .map(header -> new RequestParameterBuilder()
                                        .name(header)
                                        .in(ParameterType.HEADER)
                                        .required(false)
                                        .build()),
                        ApiHeaderConstants.getMandatoryApiHeaders().stream()
                                .map(header -> new RequestParameterBuilder()
                                        .name(header)
                                        .in(ParameterType.HEADER)
                                        .required(true)
                                        .build()))
                .collect(Collectors.toList());
    }
}