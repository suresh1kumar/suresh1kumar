package com.neom.fss.neompay.otp.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiHeaderConstants {

    public static final String HEADER_DEVICE_ALIAS = "Alias-Name";   // PHONE Brand as provided
    public static final String HEADER_APP_NAME = "App-Name";          // Which App it is CUS/MERCHANT
    public static final String HEADER_APP_VERSION = "App-Version";   // Version of App
    public static final String HEADER_PLATFORM = "Platform";     // iOS/Android
    public static final String HEADER_CLIENT_IP = "X-Forwarded-For";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_AUTHORIZATION = "Authorization";   //BEARER TOKEN
    public static final String NEOM_CHANNEL = "x-neom-channel"; // Identifies type of customer channel.
    public static final String IDEMPOTENCY_KEY = "x-idempotency-key"; // --- For ALL POST operations

    public static List<String> getApiHeaders() {
        return Arrays.asList(
            HEADER_DEVICE_ALIAS,
            HEADER_APP_NAME,
            HEADER_APP_VERSION,
            HEADER_PLATFORM,
            HEADER_CLIENT_IP,
            HEADER_USER_AGENT,
            NEOM_CHANNEL,
            IDEMPOTENCY_KEY
        );
    }

    public static List<String> getOptionalApiHeaders() {
        return Arrays.asList(
            HEADER_DEVICE_ALIAS,
            HEADER_APP_NAME,
            HEADER_APP_VERSION,
            HEADER_PLATFORM,
            HEADER_CLIENT_IP,
            HEADER_USER_AGENT
            );
    }

    public static List<String> getMandatoryApiHeaders() {
        return List.of(NEOM_CHANNEL);
    }
}
