package com.vn.entities;

public enum Status {

    STATUS_ALLOCATED,//(status of Staff is BUSY or ASSIGNED or same mean)
    STATUS_UNALLOCATED,//(status of Staff is FREE or same mean)
    STATUS_DRAFT,//(status of ClaimsRequest: Claimer(Staff) creates new ClaimsRequest and saves it as draft)
    STATUS_PENDING_APPROVAL,//(status of ClaimsRequest: Claimer(Staff) has submitted ClaimsRequest)
    STATUS_APPROVED,//(status of ClaimsRequest: PM(ROLE_APPROVER) has approved)
    STATUS_PAID,//(status of ClaimsRequest: Finances staff(ROLE_FINANCE) to paid ClaimsRequest)
    STATUS_CANCELLED;////(status of ClaimsRequest: PM(ROLE_APPROVER) rejected or Claimer cancelled)

}
