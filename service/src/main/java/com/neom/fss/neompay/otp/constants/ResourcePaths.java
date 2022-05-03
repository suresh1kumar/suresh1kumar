package com.neom.fss.neompay.otp.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourcePaths {

    public static final String API = "api";
    public static final String V1 = "/v1";
    public static final String ROOT_API = "/" + API;
    public static final String ROOT_API_V1 = ROOT_API + V1;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Card {

        public static final String NAME = "/sayHello/{name}";

        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public static class V1 {

            public static final String ROOT = ROOT_API_V1;
            public static final String SAY_HELLO = "/hello/{name}";
            public static final String GET_OTP="/otp/get";
            public static final String RESEND_OTP="/otp/resend";
            public static final String VALIDATE_OTP="/otp/validate";
            public static final String CUSTOMER_R="/save";
        }
    }
}








