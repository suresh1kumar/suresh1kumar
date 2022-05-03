package com.neom.fss.neompay.otp;

import com.neom.fss.neompay.otp.constants.OtpConstants;
import com.neom.fss.neompay.otp.controller.OtpController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTest {
    @Autowired
    OtpController otpController;

    @Test
    void contextLoads() {
        assertThat(otpController).isNotNull();
    }
}
