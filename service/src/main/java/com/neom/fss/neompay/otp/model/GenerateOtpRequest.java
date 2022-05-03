package com.neom.fss.neompay.otp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateOtpRequest {


    @NotEmpty(message = Constants.MOBILENO_VALIDATION)
    @Size(min=4,max=15, message = Constants.MOBILENO_VALIDATION)
    @Pattern(regexp = "[0-9]+",message= Constants.MOBILENO_VALIDATION)
    private String mobileNo;

    private String flag;
}
