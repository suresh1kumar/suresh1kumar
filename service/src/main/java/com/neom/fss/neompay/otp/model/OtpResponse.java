package com.neom.fss.neompay.otp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpResponse implements Serializable {

    private String refId;
    private Integer otpNumber;
    private Integer attemptsLeft;

    private Integer statusCode;
    private String errorType;
    private boolean status;
    private String message;
    private String reasonCode;


}
