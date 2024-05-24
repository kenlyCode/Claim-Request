package com.vn.service;

import com.vn.dto.EmailDTO;
import com.vn.entities.ClaimsRequest;
import com.vn.entities.Role;
import com.vn.entities.Working;
import com.vn.repository.ClaimsRequestRepository;
import com.vn.repository.WorkingRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    final private JavaMailSender javaMailSender;

    @Autowired
    WorkingRepository workingRepository;

    @Autowired
    ClaimsRequestRepository claimsRequestRepository;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendEmail(EmailDTO dto) {

        try {
//            SimpleMailMessage mailMessage
//                    = new SimpleMailMessage();
//            mailMessage.setFrom(dto.getFrom());
//            mailMessage.setTo(dto.getTo());
////            mailMessage.setText(dto.getBody());
//            mailMessage.setSubject(dto.getSubject());
//
//            // Sending the mail
//            javaMailSender.send(mailMessage);
//            return "Mail Sent Successfully...";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(dto.getFrom());
//            helper.setTo(dto.getTo());
            helper.setTo(new String[]{dto.getTo(), "dinhquangminh13@gmail.com", "vietphu200@gmail.com", "nguyenxuannamlong1517@gmail.com"});
            helper.setSubject(dto.getSubject());
            String htmlContent = "<h1>HUYGAU_NUMBERONE_MOCKPROJECTTEAM02</h1>" +
                    "<p>YoungDeveloperVN LEGENDARY</p>" +
                    "<p>Minh cày nát đội hình đối phương</p>" +
                    "<p>Phú không thể cản phá</p>" +
                    "<h2>YOU ARE VICTORY</h2>";
            helper.setText(htmlContent, true); // true to indicate HTML content

            javaMailSender.send(message);
            return "Mail Sent Successfully...";

        } catch (Exception e) {

            e.printStackTrace();
            return "Error while Sending Mail";

        }
    }

    @Override
    public String sendEmailToPM(ClaimsRequest claimsRequest) {

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("longdtvtk56@gmail.com");
            Working working = workingRepository.findByProjectIdAndRoleStaff(claimsRequest.getProject().getId(), Role.ROLE_APPROVER);
            helper.setTo(working.getStaff().getEmail());
            helper.setCc(claimsRequest.getStaff().getEmail());
            helper.setSubject("Claim Request " + claimsRequest.getProject().getName() + " " + claimsRequest.getStaff().getName() + "-" + claimsRequest.getStaff().getId());
            String htmlContent = "<p>Dear " + working.getStaff().getName() + "</p>" +
                    "<p>Claim Request for " + claimsRequest.getProject().getName() + " " + claimsRequest.getStaff().getName() + "-" + claimsRequest.getStaff().getId() + " is submitted and</p>" +
                    "<p>waiting for approval.</p>" +
                    "<p>Please access the Claim via the following link:</p>" +
                    "<a href=\"http://localhost:9090/approver/approve-claim\">http://localhost:9090/approver/approve-claim</a>" + "<br>" +
                    "<p>Sincerely,</p>" +
                    "<p>System Administrator</p>" +
                    "<p style=\"font-style: italic;\">Note: This is an auto-generated email, please do not reply.</p>";

            helper.setText(htmlContent, true); // true to indicate HTML content
            javaMailSender.send(message);
            return "Mail Sent Successfully...";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }

    }

    @Override
    public String sendEmailToFinance(ClaimsRequest claimsRequest) {
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("longdtvtk56@gmail.com");
            Working working = workingRepository.findByProjectIdAndRoleStaff(claimsRequest.getProject().getId(), Role.ROLE_FINANCE);
            helper.setTo(working.getStaff().getEmail());
            helper.setCc(claimsRequest.getStaff().getEmail());
            helper.setSubject("Pending Approval Claims");
            String htmlContent = "<p>Dear " + working.getStaff().getName() + "</p>" +
                    "<p>There is/are Claim(s) for the Staff below that has been pending for your approval:</>" +
                    "<p>Claim Request for " + claimsRequest.getProject().getName() + " " + claimsRequest.getStaff().getName() + "-" + claimsRequest.getStaff().getId() + " is approved and</p>" +
                    "<p>pending for your action.</p>" +
                    "<p>Please access the Claim via the following link:</p>" +
                    "<a href=\"http://localhost:9090/finance/paid-claim\">http://localhost:9090/finance/paid-claim</a>" + "<br>" +
                    "<p>Sincerely,</p>" +
                    "<p>System Administrator</p>" +
                    "<p style=\"font-style: italic;\">Note: This is an auto-generated email, please do not reply.</p>";

            helper.setText(htmlContent, true); // true to indicate HTML content
            javaMailSender.send(message);
            return "Mail Sent Successfully...";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }

    }

    // ET 3 tr39
    @Override
    public String sendEmailToCreaterWhenClaimRequestApproved(ClaimsRequest claimsRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("longdtvtk56@gmail.com");
            helper.setTo(claimsRequest.getStaff().getEmail());

            helper.setSubject("Claim Request " + claimsRequest.getProject().getName() + " " + claimsRequest.getStaff().getName() + " - " + claimsRequest.getStaff().getId());
            String htmlContent = "<p>Dear " + claimsRequest.getStaff().getName() + ",</p>" +
                    "<br>" +
                    "<p>Claim Request for " + claimsRequest.getProject().getName() + " " + claimsRequest.getStaff().getName() + "-" + claimsRequest.getStaff().getId() + " is approved by approver.</p>" +
                    "<br>" +
                    "<p>Please access the Claim via the following link:</p>" +
                    "<a href=\"http://localhost:9090/employee/view-claim-approved\">http://localhost:9090/employee/view-claim-approved</a>" + "<br>" +
                    "<br>" +
                    "<p>Sincerely,</p>" +
                    "<p>System Administrator</p>" +
                    "<p style=\"font-style: italic;\">Note: This is an auto-generated email, please do not reply.</p>";

            helper.setText(htmlContent, true); // true to indicate HTML content
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }

    //    ET 4 tr39
    @Override
    public String sendEmailFromToCreaterWhenClaimRequestReturn(ClaimsRequest claimsRequest) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("longdtvtk56@gmail.com");
            helper.setTo(claimsRequest.getStaff().getEmail());

            helper.setSubject("Claim Request " + claimsRequest.getProject().getName() + " " + claimsRequest.getStaff().getName() + " - " + claimsRequest.getStaff().getId());
            String htmlContent = "<p>Dear " + claimsRequest.getStaff().getName() + ",</p>" +
                    "<br>" +
                    "<p>Claim Request for " + claimsRequest.getProject().getName() + " " + claimsRequest.getStaff().getName() + "-" + claimsRequest.getStaff().getId() + " is returned.</p>" +
                    "<br>" +
                    "<p>Please access the Claim via the following link:</p>" +
                    "<a href=\"http://localhost:9090/employee/view-claim-draft\">http://localhost:9090/employee/view-claim-draft</a>" + "<br>" +
                    "<br>" +
                    "<p>Sincerely,</p>" +
                    "<p>System Administrator</p>" +
                    "<p style=\"font-style: italic;\">Note: This is an auto-generated email, please do not reply.</p>";

            helper.setText(htmlContent, true); // true to indicate HTML content
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }
}
