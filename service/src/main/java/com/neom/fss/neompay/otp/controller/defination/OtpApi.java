package com.neom.fss.neompay.otp.controller.defination;

import com.neom.fss.neompay.otp.model.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import static com.neom.fss.neompay.otp.constants.ApiHeaderConstants.NEOM_CHANNEL;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

public interface OtpApi {
    @ApiOperation(value = "Generate Otp")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = OtpResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "External API Error")
    })
    public ResponseEntity<Object> getOTP(@RequestHeader(NEOM_CHANNEL) String neomChannel,String otpServiceCode, String mobileNo) throws NoSuchAlgorithmException;
    @ApiOperation(value = "Resend Otp")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = OtpResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "External API Error")
    })
    public ResponseEntity<Object> reSendOTP(@RequestHeader(NEOM_CHANNEL) String neomChannel,String refId) throws NoSuchAlgorithmException;
    @ApiOperation(value = "Validate Otp")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = ValidateOtpResponse.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 400, message = "Bad Request."),
            @ApiResponse(code = 500, message = "External API Error")
    })
    public ResponseEntity<Object> validateOTP(@RequestHeader(NEOM_CHANNEL) String neomChannel,@Valid ValidateOtpRequest request);
}
