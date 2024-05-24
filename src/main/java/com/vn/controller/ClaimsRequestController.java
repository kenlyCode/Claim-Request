package com.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ClaimsRequestController {
    @GetMapping("/employee/create-claim")
    public String pageClaimsRequestCreate() {
        return "/employee/create-claim";
    }

    @GetMapping("/employee/view-claim-draft")
    public String pageClaimsRequestDraff() {

        return "/employee/view-claim-draft";//về trang view-claim(draft) để edit hoặc submit

    }


    @GetMapping("/employee/update-claim/{id}")//id của claimsRequest để có thể edit
    public String pageClaimsRequestUpdate(@PathVariable("id") Integer id) {

        return "/employee/update-claim";

    }

    @GetMapping("/employee/view-claim-cancelled")
    public String pageClaimRequestStatusCancelled() {
        return "/employee/view-claim-cancelled";
    }

    @GetMapping("/employee/view-claim-pending-approval")
    public String pageClaimRequestStatusPendingApproval() {
        return "/employee/view-claim-pending-approval";
    }

    @GetMapping("/employee/view-claim-approved")
    public String pageClaimRequestStatusApproved() {
        return "/employee/view-claim-approved";
    }

    @GetMapping("/employee/view-claim-paid")
    public String pageClaimRequestStatusPaid() {
        return "/employee/view-claim-paid";
    }


    @GetMapping("/finance/paid-claim")
    public String pageFinanceApproved() {
        
        return "finance/paid-claim";

    }

    @GetMapping("/finance/view-claim-paid")
    public String pageFinancePaid() {

        return "/finance/view-claim-paid";

    }

    // Approver Part
    @GetMapping("/approver/approve-claim")
    public String pageVetting() {
        return "/approver/approve-claim";
    }

    @GetMapping("approver/view-claim-approved-or-paid")
    public String pageApprovedOrPaid() {
        return "/approver/view-claim-approved-or-paid";
    }
}
