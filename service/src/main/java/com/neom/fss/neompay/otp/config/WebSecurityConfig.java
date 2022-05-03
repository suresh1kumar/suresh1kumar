package com.neom.fss.neompay.otp.config;



import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/v3/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/actuator/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http.authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/token").permitAll()
                    .antMatchers(HttpMethod.GET, "/url").permitAll()
                    .antMatchers(HttpMethod.POST, "/verify").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/util/**")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/util/**").permitAll()
                    .antMatchers(HttpMethod.PUT, "/util/**").permitAll()
                    .antMatchers("/v3/api-docs", "/v2/api-docs", "/configuration/ui",
                            "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling()
                    .accessDeniedHandler(getAccessDeniedHandler());


        //With JWT token application is immune to CSRF, so disabling it
        http.csrf().disable();

        //Switch off session creation
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private AccessDeniedHandlerImpl getAccessDeniedHandler() {
        AccessDeniedHandlerImpl handler = new AccessDeniedHandlerImpl();
        handler.setErrorPage(null);
        return handler;
    }


}