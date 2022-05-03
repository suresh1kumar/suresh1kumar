package com.neom.fss.neompay.otp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neom.fss.neompay.otp.model.*;
import com.neom.fss.neompay.otp.repository.entity.OtpInfo;
import com.neom.fss.neompay.otp.service.impl.OtpServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Tag("OTP")
@SpringBootTest
@AutoConfigureMockMvc
 class OtpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OtpServiceImpl otpService;

    OtpInfo otpInfo = new OtpInfo();
    HttpHeaders httpHeaders = new HttpHeaders();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        httpHeaders.add(HttpHeaders.AUTHORIZATION,"Bearer token");
        httpHeaders.add("x-neom-channel", "app");
    }

    public OtpControllerTest(){
        otpInfo.setRefId("163785308784443210");
        otpInfo.setUserIdentity("9876543210");
        otpInfo.setOtpNumber(123456);
        otpInfo.setGeneratedTime(LocalDateTime.now());
        otpInfo.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpInfo.setAttemptsLeft(3);
    }

    @Test
    @DisplayName("Generate OTP")
    void getOTP() throws Exception {
        when(otpService.getOTP("signup_c","9876543210")).thenReturn(ResponseEntity.status(HttpStatus.OK).body(new OtpResponse()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/util/v1.0/otp")
                        .param("otpServiceCode","SIGNUP_C")
                        .param("mobileNo","9876543210")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Resend OTP")
    void resendOTP() throws Exception {
        when(otpService.reSendOTP("163785308784443210")).thenReturn(ResponseEntity.status(HttpStatus.OK).body(new OtpResponse()));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/util/v1.0/otp/163785308784443210")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(httpHeaders))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("validate OTP")
    void validateOTP() throws Exception {
        ValidateOtpRequest request=ValidateOtpRequest.builder()
                .refId("163785308784443210")
                .otpNumber("123456")
                .build();

        when(otpService.validateOTP(request)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(new ValidateOtpResponse()));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/util/v1.0/otp")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .headers(httpHeaders))
                        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("validate OTP Bad request")
    void validateOTPBadRequest() throws Exception {
        ValidateOtpRequest request=ValidateOtpRequest.builder()
                .refId("163785308784443210")
                .build();

        when(otpService.validateOTP(request)).thenReturn(ResponseEntity.status(HttpStatus.OK).body(new ValidateOtpResponse()));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/util/v1.0/otp")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .headers(httpHeaders))
                .andExpect(status().isBadRequest());
    }
}
