package com.vn.service;

import com.vn.dto.ClaimsRequestDTO;
import com.vn.dto.ProjectDTO;
import com.vn.dto.StaffDTO;
import com.vn.entities.ClaimsRequest;
import com.vn.entities.Role;
import com.vn.entities.Status;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface ClaimsRequestService {
    ClaimsRequest create(ClaimsRequestDTO claimsRequestDTO) throws ParseException;
    ClaimsRequest setStatus(ClaimsRequestDTO claimsRequestDTO, Status status) throws ParseException;

    List<ClaimsRequestDTO> getAllClaimRequestStatus(Integer staffId, Role role, Status status) throws ParseException;

    ClaimsRequest update(ClaimsRequestDTO claimsRequestDTO) throws ParseException;

    ClaimsRequestDTO getById(Integer id);

    List<ClaimsRequestDTO> getByStaffId(Integer staffId) throws ParseException;

    List<ClaimsRequestDTO> getDraftClaimRequestBystaffId(Integer staffId);

    List<ClaimsRequestDTO> getCancelledClaimsRequest(Integer staffId);

    List<ClaimsRequestDTO> getApprovedClaimsRequestStaffId(Integer staffId);

    List<ClaimsRequestDTO> getPaidClaimRequestStaffId(Integer staffId);

    List<ClaimsRequestDTO> getPendingApprovalClaimRequestStaffId(Integer staffId);

    List<ClaimsRequestDTO>getAllClaimRequestStatusApproved(Integer id);
    List<ClaimsRequestDTO>getAllClaimRequestStatusPaid(Integer id);

    List<ClaimsRequestDTO> getAllPendingClaims();

    List<ClaimsRequestDTO> getAllClaimsRequestByStatus(Status status);

}
