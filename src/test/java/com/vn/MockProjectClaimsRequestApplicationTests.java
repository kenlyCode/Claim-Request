package com.vn;

import com.vn.dto.EmailDTO;
import com.vn.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MockProjectClaimsRequestApplicationTests {

    @Autowired
    public EmailService emailService;

    @Test
    void contextLoads() {
        EmailDTO dto = new EmailDTO();
        dto.setSubject("MOCK PROJECT TEAM 02 (HUYGAUNUMBERONE)");
        dto.setFrom("longdtvtk56@gmail.com");
        dto.setTo("youngdeveloper3012@gmail.com");
//        dto.setTo("nguyenxuannamlong1517@gmail.com");
        dto.setBody("test email");

        String result = emailService.sendEmail(dto);
        System.out.println(result);
    }

}
