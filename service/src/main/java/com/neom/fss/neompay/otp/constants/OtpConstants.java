package com.neom.fss.neompay.otp.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum OtpConstants {

    HELLO("Hello "),
    SPACE_AS_STRING(" "),
    UNKNOWN("unknown"),
    SERVER_ERROR("SERVER_ERROR");

    private String value;
}
