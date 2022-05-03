package com.neom.fss.neompay.otp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOtpRequest {


    @NotEmpty(message="This filed is required")
    private String refId;

    @NotNull(message=Constants.OTP_VALIDATIONS)
    @Size(min = 6,max=6, message = Constants.OTP_VALIDATIONS)
    @Pattern(regexp = "^[0-9]+",message = Constants.OTP_VALIDATIONS)
    private String otpNumber;
}
