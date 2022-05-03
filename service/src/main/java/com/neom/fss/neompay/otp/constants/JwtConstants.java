package com.neom.fss.neompay.otp.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum JwtConstants {
    JWT_COOKIE_NAME("JRNTOKEN"),
    JWT_TOKEN_NAME("Authorization"),
    AUTH_BEARER("Bearer "),
    CORRELATION_ID_HEADER_NAME("x-correlation-id"),
    CORRELATION_ID_LOG_VAR_NAME("correlationId"),
    LOCALHOST_IPV4("127.0.0.1"),
    LOCALHOST_IPV6("0:0:0:0:0:0:0:1"),
    IP_ADDRESS("ipAddress"),
    JWT_VERIFICATION_ENDPOINT("/token/verify"),
    JWT_GENERATE_ENDPOINT("/token/encrypt/sign"),
    JWT_RETRIEVE_ENDPOINT("/token/retrieveAccessToken");

    private String value;
}
