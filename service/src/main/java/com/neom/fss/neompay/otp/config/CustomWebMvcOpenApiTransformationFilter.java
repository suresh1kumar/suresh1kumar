package com.neom.fss.neompay.otp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1000)
public class CustomWebMvcOpenApiTransformationFilter implements WebMvcOpenApiTransformationFilter {

    public static final String API_NEOMPAY_SBX = "api.neompay-sbx";

    @Value("${sbx.proxy-address}")
    private String sbxProxyAddress;

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        OpenAPI openApi = context.getSpecification();
        Server server = openApi.getServers().get(0);

        if (server.getUrl().contains(API_NEOMPAY_SBX)) {
            server.setUrl(sbxProxyAddress);
        }
        openApi.setServers(List.of(server));

        return openApi;
    }

    @Override
    public boolean supports(@NonNull DocumentationType delimiter) {
        return delimiter == DocumentationType.OAS_30;
    }
}
