package com.neom.fss.neompay.otp.controller;

import com.neom.fss.neompay.otp.config.SwaggerConfig;
import com.neom.fss.neompay.otp.controller.defination.OtpApi;
import com.neom.fss.neompay.otp.model.*;
import com.neom.fss.neompay.otp.repository.CustomerRepository;
import com.neom.fss.neompay.otp.service.OtpService;
import com.neom.fss.neompay.otp.service.impl.OtpServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

import static com.neom.fss.neompay.otp.constants.ApiHeaderConstants.NEOM_CHANNEL;

@RestController
@RequestMapping("/util/v1.0")
@Validated
@Api(value = "/api", tags = {SwaggerConfig.ServiceTags.OTP_SERVICE})
@Flogger
public class OtpController implements OtpApi {
    @Autowired
    CustomerRepository customerRepository;
    private final OtpService otpService;

    @Autowired
    public OtpController(OtpServiceImpl otpService) {
        this.otpService = otpService;
    }

    @Override
    @GetMapping(value = "/otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getOTP(@RequestHeader(NEOM_CHANNEL) String neomChannel,@RequestParam String otpServiceCode,@RequestParam String mobileNo) throws NoSuchAlgorithmException {

        return otpService.getOTP(otpServiceCode,mobileNo);
    }

    @Override
    @GetMapping(value = "/otp/{refId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> reSendOTP(@RequestHeader(NEOM_CHANNEL) String neomChannel,@PathVariable String refId) throws NoSuchAlgorithmException {

        return otpService.reSendOTP(refId);
    }

    @Override
    @PostMapping(value = "/otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateOTP(@RequestHeader(NEOM_CHANNEL) String neomChannel,@Valid @RequestBody ValidateOtpRequest request) {
        return otpService.validateOTP(request);
    }

}
