package com.vn.api;

import com.vn.dto.ClaimsRequestDTO;
import com.vn.entities.ClaimsRequest;
import com.vn.entities.Role;
import com.vn.entities.Status;
import com.vn.repository.ClaimsRequestRepository;
import com.vn.service.ClaimsRequestService;
import com.vn.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class ClaimsRequestControllerAPI {

    @Autowired
    ClaimsRequestService claimsRequestService;

    @Autowired
    ClaimsRequestRepository claimsRequestRepository;

    @Autowired
    EmailService emailService;

    @PostMapping("/api/claim/employee")
    public List<ClaimsRequestDTO> createClaimsRequest(@RequestBody List<ClaimsRequestDTO> claimsRequestDTOS) throws ParseException {

        for (ClaimsRequestDTO claimsRequestDTO : claimsRequestDTOS) {
            claimsRequestService.create(claimsRequestDTO);
        }

        return claimsRequestDTOS;

    }

    @GetMapping("/api/claim/{id}/employee")
    public ClaimsRequestDTO getInfomationClaimsRequest(@PathVariable("id") Integer id) {

        return claimsRequestService.getById(id);
    }

    //lấy dữ liệu của tất cả claim theo staff
    @GetMapping("/api/claim/staff/{staffId}/employee")
    public List<ClaimsRequestDTO> getAllClaimsRequest(@PathVariable("staffId") Integer staffId) throws ParseException {

        return claimsRequestService.getByStaffId(staffId);

    }

    //lấy dữ liệu của tất cả claim có trạng thái draff theo staff để edit or submit
    //chỗ này chưa hoàn thiện đang ngâm cứu (admin có thể view ở đây nên chả việc gì phải chặn api)
    @GetMapping("/api/claim/draft/staff/{staffId}/employee") //do link trung nen co y dat them "draft"
    public List<ClaimsRequestDTO> getAllClaims(@PathVariable("staffId") Integer id) {
        if (id != 1) {
            return claimsRequestService.getDraftClaimRequestBystaffId(id);

        } else {
            return claimsRequestService.getAllClaimsRequestByStatus(Status.STATUS_DRAFT);
        }


    }

    //hàm này hàm chung nên là không cần theo chuẩn lắm và chặn admin là đủ
    @PutMapping("/api/claim")
    public ClaimsRequest updateClaimsRequest(@RequestBody ClaimsRequestDTO claimsRequestDTO) throws ParseException {

        ClaimsRequest claimsRequest = claimsRequestService.update(claimsRequestDTO);

        if (claimsRequest != null) {
            if (claimsRequest.getStatus() == Status.STATUS_PENDING_APPROVAL) {
                emailService.sendEmailToPM(claimsRequest);
            } else if (claimsRequest.getStatus() == Status.STATUS_APPROVED) {
                emailService.sendEmailToFinance(claimsRequest);
                emailService.sendEmailToCreaterWhenClaimRequestApproved(claimsRequest);
            } else if (claimsRequest.getStatus() == Status.STATUS_DRAFT) {
                emailService.sendEmailFromToCreaterWhenClaimRequestReturn(claimsRequest);
            }
        }

        return claimsRequest;

    }

    @GetMapping("/api/claim/pending-approved/staff/{staffId}/approver")
    public List<ClaimsRequestDTO> getAllPendingClaimsFollowRole(@PathVariable("staffId") Integer staffId) throws ParseException {
        return claimsRequestService.getAllClaimRequestStatus(staffId, Role.ROLE_APPROVER, Status.STATUS_PENDING_APPROVAL);
    }

    @GetMapping("/api/claim/approved/staff/{staffId}/approver")
    public List<ClaimsRequestDTO> getAllApprovedClaimsFollowRole(@PathVariable("staffId") Integer staffId) throws ParseException {
        return claimsRequestService.getAllClaimRequestStatus(staffId, Role.ROLE_APPROVER, Status.STATUS_APPROVED);
    }

    @GetMapping("/api/claim/paid/staff/{staffId}/approver")
    public List<ClaimsRequestDTO> getAllPaidClaimsFollowRole(@PathVariable("staffId") Integer staffId) throws ParseException {
        return claimsRequestService.getAllClaimRequestStatus(staffId, Role.ROLE_APPROVER, Status.STATUS_PAID);
    }

    @GetMapping("/api/claim/cancelled-or-rejected/staff/{staffId}/employee")
    public List<ClaimsRequestDTO> getAllClaimHaveStatusCancelled(@PathVariable("staffId") Integer id) {
        if (id != 1) {
            return claimsRequestService.getCancelledClaimsRequest(id);
        } else {
            return claimsRequestService.getAllClaimsRequestByStatus(Status.STATUS_CANCELLED);
        }
    }

    @GetMapping("/api/claim/approved/staff/{staffId}/employee")
    public List<ClaimsRequestDTO> getAllClaimHaveStatusApproved(@PathVariable("staffId") Integer id) {
        if (id != 1) {
            return claimsRequestService.getApprovedClaimsRequestStaffId(id);
        } else {
            return claimsRequestService.getAllClaimsRequestByStatus(Status.STATUS_APPROVED);
        }
    }

    @GetMapping("/api/claim/pending-approval/staff/{staffId}/employee")
    public List<ClaimsRequestDTO> getAllClaimHaveStatuspendingApproval(@PathVariable("staffId") Integer id) {
        if (id != 1) {
            return claimsRequestService.getPendingApprovalClaimRequestStaffId(id);
        } else {
            return claimsRequestService.getAllClaimsRequestByStatus(Status.STATUS_PENDING_APPROVAL);
        }

    }

    @GetMapping("/api/claim/paid/staff/{staffId}/employee")
    public List<ClaimsRequestDTO> getAllClaimHaveStatusPaid(@PathVariable("staffId") Integer id) {
        if (id != 1) {
            return claimsRequestService.getPaidClaimRequestStaffId(id);
        } else {
            return claimsRequestService.getAllClaimsRequestByStatus(Status.STATUS_PAID);
        }


    }

    @GetMapping("/api/claim/approved/staff/{staffId}/finance")
    public List<ClaimsRequestDTO> getAllClaimsStatusApproved(@PathVariable("staffId") Integer id) {
        return claimsRequestService.getAllClaimRequestStatusApproved(id);
    }

    @GetMapping("/api/claim/paid/staff/{staffId}/finance")
    public List<ClaimsRequestDTO> getAllClaimsStatusPaid(@PathVariable("staffId") Integer id) {
        return claimsRequestService.getAllClaimRequestStatusPaid(id);
    }

}
