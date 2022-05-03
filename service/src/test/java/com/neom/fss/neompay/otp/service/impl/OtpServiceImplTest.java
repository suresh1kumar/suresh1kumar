package com.neom.fss.neompay.otp.service.impl;

import com.neom.fss.neompay.otp.model.ReSendOtpRequest;
import com.neom.fss.neompay.otp.model.ValidateOtpRequest;
import com.neom.fss.neompay.otp.repository.*;
import com.neom.fss.neompay.otp.repository.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
 class OtpServiceImplTest {

    @InjectMocks
    OtpServiceImpl otpService;
    @Mock
    OtpRepository otpRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    MerchantRepository merchantRepository;
    @Mock
    MerchantAppRepository merchantAppRepository;


    @Mock
    BlockedCustomerRepository blockedCustomerRepository;
    @Test
    @DisplayName("Generate OTP Customer - Success")
    void generateOTPTest() throws NoSuchAlgorithmException {
        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("9876543555")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(3).build();
        when(customerRepository.findByMobileNo("9876543210")).thenReturn(null);
        when(otpRepository.save(any(OtpInfo.class))).thenReturn(otpInfo);
        ResponseEntity response = otpService.getOTP("signup_c","9876543210");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }


    @Test
    @DisplayName("Generate OTP Merchant- Success")
    void generateOtpMerchantTest() throws NoSuchAlgorithmException {
        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("9876543554")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(3).build();
        when(merchantRepository.findByMobileNo("9876543210")).thenReturn(null);
        when(otpRepository.save(any(OtpInfo.class))).thenReturn(otpInfo);
        ResponseEntity response = otpService.getOTP("signup_m","9876543210");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    @DisplayName("Generate OTP Merchant Application - Success")
    void generateOtpMerchantAppTest() throws NoSuchAlgorithmException {
        MerchantApplication application=MerchantApplication.builder().mobileNo("9876543210").build();
        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("9876543210")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(3).build();
        when(merchantAppRepository.findByMobileNo("9876543210")).thenReturn(application);
        when(otpRepository.save(any(OtpInfo.class))).thenReturn(otpInfo);
        ResponseEntity response = otpService.getOTP("merchant_app","9876543210");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }


    @Test
    @DisplayName("Generate Customer Login Reset")
    void generateOtpResetPasscodeTest() throws NoSuchAlgorithmException {
        Customer customer= Customer.builder().customerId("100000000001").mobileNo("9876543554").build();
        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("9876543554")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(3).build();
        when(customerRepository.findByMobileNo("9876543210")).thenReturn(customer);
        when(otpRepository.save(any(OtpInfo.class))).thenReturn(otpInfo);
        ResponseEntity response = otpService.getOTP("login_reset_c","9876543210");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

    @Test
    @DisplayName("Generate Customer Login Reset - Not Found")
    void generateOtpResetPasscodeNoTest() throws NoSuchAlgorithmException {
        when(customerRepository.findByMobileNo("9876543210")).thenReturn(null);
        ResponseEntity response = otpService.getOTP("login_reset_c","9876543210");
        assertThat(response.getStatusCodeValue()).isEqualTo(404);

    }
    @Test
    @DisplayName("Generate OTP - Negative")
    void generateOtpMerchantTestNegative() throws NoSuchAlgorithmException {
        Merchant merchant=Merchant.builder()
                .mobileNo("9876543554").build();
        when(merchantRepository.findByMobileNo("9876543554")).thenReturn(merchant);
        ResponseEntity response = otpService.getOTP("signup_m","9876543554");
        assertThat(response.getStatusCodeValue()).isEqualTo(208);

    }


    @Test
    @DisplayName("Generate OTP Merchant Application - Not Found")
    void generateOtpMerchantAppNoTest() throws NoSuchAlgorithmException {
        when(merchantAppRepository.findByMobileNo("9876543210")).thenReturn(null);
        ResponseEntity response = otpService.getOTP("merchant_app","9876543210");
        assertThat(response.getStatusCodeValue()).isEqualTo(404);

    }

    @Test
    @DisplayName("Generate OTP-User Exists")
    void generateOTPUserExistTest() throws NoSuchAlgorithmException {
         Customer customer = Customer.builder()
                .customerId("100000001")
                .mobileNo("9876543210").build();
        when(customerRepository.findByMobileNo("9876543210")).thenReturn(customer);
        ResponseEntity response = otpService.getOTP("signup_c","9876543210");
        assertThat(response.getStatusCodeValue()).isEqualTo(208);
    }

    @Test
    @DisplayName("Generate OTP-Other")
    void generateOTPOtherTest() throws NoSuchAlgorithmException {

        when(otpRepository.save(any())).thenReturn(new OtpInfo());
        ResponseEntity response = otpService.getOTP("test","9876543210");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }




    @Test
    @DisplayName("Resend OTP - Success")
    void resendOTPTest() throws NoSuchAlgorithmException {
        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("1234567890")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(2).build();

        when(otpRepository.findById("1234567890")).thenReturn(Optional.ofNullable(otpInfo));
        when(otpRepository.save(any())).thenReturn(otpInfo);
        ResponseEntity response = otpService.reSendOTP("1234567890");
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @DisplayName("Resend OTP- Max Attempts")
    void resendMaxAttemptsOTPTest() throws NoSuchAlgorithmException {
        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("1234567890")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(0).build();
        ReSendOtpRequest request = new ReSendOtpRequest();
        request.setRefId("1234567890");
        when(otpRepository.findById("1234567890")).thenReturn(Optional.ofNullable(otpInfo));
        ResponseEntity response = otpService.reSendOTP("1234567890");
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    @DisplayName("Resend OTP - Not Found")
    void resendNotAvailableOTPTest() throws NoSuchAlgorithmException {
        ReSendOtpRequest request = new ReSendOtpRequest();
        request.setRefId("1234567890");
        when(otpRepository.findById("1234567890")).thenReturn(Optional.ofNullable(null));
        ResponseEntity response = otpService.reSendOTP("1234567890");
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }


    @Test
    @DisplayName("Validate OTP - Success")
    void validateOTPTest() {
        ValidateOtpRequest request = ValidateOtpRequest.builder()
                .refId("1234567890")
                .otpNumber("123456")
                .build();

        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("1234567890")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(3).build();

        when(otpRepository.findById("1234567890")).thenReturn(Optional.ofNullable(otpInfo));
        when(otpRepository.save(any())).thenReturn(otpInfo);
        ResponseEntity response = otpService.validateOTP(request);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    @DisplayName("Blocked Customer")
    void blockedCustomerTest() throws NoSuchAlgorithmException {
        BlockedCustomer customer = BlockedCustomer.builder()
                .userIdentifier("9876543555")
                .blockTime(LocalDateTime.now().plusMinutes(5)).build();
        when(blockedCustomerRepository.findByIdentifier("9876543210")).thenReturn(customer);
        ResponseEntity response = otpService.getOTP("signup_c","9876543210");
        assertThat(response.getStatusCodeValue()).isEqualTo(400);

    }




    @Test
    @DisplayName("Validate OTP - Success")
    void otpNotFoundTest() {
        ValidateOtpRequest request = ValidateOtpRequest.builder()
                .refId("1234567890")
                .otpNumber("123765")
                .build();

        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("1234567890")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attemptsLeft(3).build();

        when(otpRepository.findById("1234567890")).thenReturn(Optional.ofNullable(otpInfo));

        ResponseEntity response = otpService.validateOTP(request);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
    @Test
    @DisplayName("Validate OTP - Not Found ")
    void inValidateOTPTest() {
        ValidateOtpRequest request = ValidateOtpRequest.builder()
                .refId("1234567890")
                .otpNumber("123456")
                .build();
        when(otpRepository.findById("1234567890")).thenReturn(Optional.ofNullable(null));
        ResponseEntity response = otpService.validateOTP(request);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }

    @Test
    @DisplayName("Validate OTP - Expired")
    void expiredOTPTest() {
        ValidateOtpRequest request = ValidateOtpRequest.builder()
                .refId("1234567890")
                .otpNumber("123456")
                .build();
        OtpInfo otpInfo = OtpInfo.builder()
                .refId("1234567890")
                .userIdentity("1234567890")
                .otpNumber(123456)
                .generatedTime(LocalDateTime.now())
                .expiryTime(LocalDateTime.now().minusMinutes(2))
                .attemptsLeft(3).build();

        when(otpRepository.findById("1234567890")).thenReturn(Optional.ofNullable(otpInfo));
        ResponseEntity response = otpService.validateOTP(request);
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
    }
}
