package com.vn.service;

import com.vn.dto.EmailDTO;
import com.vn.entities.ClaimsRequest;

public interface EmailService {

    String sendEmail(EmailDTO dto);

    String sendEmailToPM(ClaimsRequest claimsRequest);

    String sendEmailToFinance(ClaimsRequest claimsRequest);

    String sendEmailToCreaterWhenClaimRequestApproved(ClaimsRequest claimsRequest);

    String sendEmailFromToCreaterWhenClaimRequestReturn(ClaimsRequest claimsRequest);
}
