package com.vn.service;

import com.vn.dto.ClaimsRequestDTO;
import com.vn.dto.ProjectDTO;
import com.vn.dto.StaffDTO;
import com.vn.entities.*;
import com.vn.repository.ClaimsRequestRepository;
import com.vn.repository.ProjectRepository;
import com.vn.repository.StaffRepository;
import com.vn.repository.WorkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClaimsRequestServiceImpl implements ClaimsRequestService {

    @Autowired
    ClaimsRequestRepository claimsRequestRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    WorkingRepository workingRepository;

    @Override
    public ClaimsRequest create(ClaimsRequestDTO claimsRequestDTO) throws ParseException {

        ClaimsRequest claimsRequest = new ClaimsRequest();
        Staff staff = staffRepository.findById(claimsRequestDTO.getStaffDTO().getId()).orElse(null);
        Project project = projectRepository.findById(claimsRequestDTO.getProjectDTO().getId()).orElse(null);

        claimsRequest.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(claimsRequestDTO.getDate()));
        claimsRequest.setDay(claimsRequestDTO.getDay());
        claimsRequest.setFromTime(claimsRequestDTO.getFromTime());
        claimsRequest.setToTime(claimsRequestDTO.getToTime());
        claimsRequest.setTotalHours(claimsRequestDTO.getTotalHours());
        claimsRequest.setRemarks(claimsRequestDTO.getRemarks());
        claimsRequest.setStatus(Status.STATUS_DRAFT);
        claimsRequest.setStaff(staff);
        claimsRequest.setProject(project);

        return claimsRequestRepository.save(claimsRequest);

    }

    @Override
    public ClaimsRequest setStatus(ClaimsRequestDTO claimsRequestDTO, Status status) throws ParseException {
        ClaimsRequest claimsRequestrequest = claimsRequestRepository.findById(claimsRequestDTO.getId()).orElse(null);
        claimsRequestrequest.setStatus(status);
        return claimsRequestRepository.save(claimsRequestrequest);
    }

    @Override
    public List<ClaimsRequestDTO> getAllClaimRequestStatus(Integer staffId, Role role, Status status) throws ParseException {

        List<ClaimsRequestDTO> claimDTOs = new ArrayList<>();

        List<ClaimsRequest> claims = new ArrayList<>();

        List<Working> workings = workingRepository.findByStaffIdAndRoleStaff(staffId, role);

        for (Working working : workings) {
            List<ClaimsRequest> listElement = claimsRequestRepository.findByProjectIdAndStatus(working.getProjectId(), status);
            for (ClaimsRequest c : listElement) {
                claims.add(c);
            }
        }

        for (ClaimsRequest claim : claims) {
            // Check if the staffDTOId matches the claim's staffDTOId

            Staff staff = staffRepository.findById(claim.getStaffId()).orElse(null);
            Project project = projectRepository.findById(claim.getProjectId()).orElse(null);

            if (staff != null && project != null) {
                ClaimsRequestDTO claimDTO = new ClaimsRequestDTO();
                claimDTO.setId(claim.getId());
                claimDTO.setStatus(String.valueOf(claim.getStatus()));

                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setId(staff.getId());
                staffDTO.setName(staff.getName());
                claimDTO.setStaffDTO(staffDTO);

                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(project.getId());
                projectDTO.setName(project.getName());
                projectDTO.setFromDate(String.valueOf(project.getFromDate()));
                projectDTO.setToDate(String.valueOf(project.getToDate()));
                claimDTO.setProjectDTO(projectDTO);

                claimDTO.setDay(claim.getDay());
                claimDTO.setDate(String.valueOf(claim.getDate()));
                claimDTO.setFromTime(claim.getFromTime());
                claimDTO.setToTime(claim.getToTime());
                claimDTO.setTotalHours(claim.getTotalHours());
                claimDTO.setRemarks(claim.getRemarks());

                claimDTOs.add(claimDTO);
            }
        }

        return claimDTOs;
    }
    @Override
    public ClaimsRequest update(ClaimsRequestDTO claimsRequestDTO) throws ParseException {

        ClaimsRequest claimsRequest = new ClaimsRequest();
        Staff staff = staffRepository.findById(claimsRequestDTO.getStaffDTO().getId()).orElse(null);
        Project project = projectRepository.findById(claimsRequestDTO.getProjectDTO().getId()).orElse(null);

        claimsRequest.setId(claimsRequestDTO.getId());
        claimsRequest.setStatus(Status.valueOf(claimsRequestDTO.getStatus()));
        claimsRequest.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(claimsRequestDTO.getDate()));
        claimsRequest.setDay(claimsRequestDTO.getDay());
        claimsRequest.setFromTime(claimsRequestDTO.getFromTime());
        claimsRequest.setToTime(claimsRequestDTO.getToTime());
        claimsRequest.setTotalHours(claimsRequestDTO.getTotalHours());
        claimsRequest.setRemarks(claimsRequestDTO.getRemarks());
        claimsRequest.setStaff(staff);
        claimsRequest.setProject(project);

        return claimsRequestRepository.save(claimsRequest);

    }

    @Override
    public ClaimsRequestDTO getById(Integer id) {
        ClaimsRequest claimsRequest = claimsRequestRepository.findById(id).orElse(null);
        StaffDTO staffDTO = new StaffDTO();
        ProjectDTO projectDTO = new ProjectDTO();
        ClaimsRequestDTO claimsRequestDTO = new ClaimsRequestDTO();

        staffDTO.setId(claimsRequest.getStaffId());
        staffDTO.setName(claimsRequest.getStaff().getName());
        staffDTO.setDepartment(claimsRequest.getStaff().getDepartment());
        staffDTO.setRole(String.valueOf(workingRepository.findByStaffIdAndProjectId(claimsRequest.getStaffId(), claimsRequest.getProjectId()).getRoleStaff()));

        projectDTO.setId(claimsRequest.getProjectId());
        projectDTO.setName(claimsRequest.getProject().getName());
        projectDTO.setFromDate(String.valueOf(claimsRequest.getProject().getFromDate()));
        projectDTO.setToDate(String.valueOf(claimsRequest.getProject().getToDate()));

        claimsRequestDTO.setStaffDTO(staffDTO);
        claimsRequestDTO.setProjectDTO(projectDTO);
        claimsRequestDTO.setId(claimsRequest.getId());
        claimsRequestDTO.setStatus(String.valueOf(claimsRequest.getStatus()));
        claimsRequestDTO.setDate(String.valueOf(claimsRequest.getDate()));
        claimsRequestDTO.setDay(claimsRequest.getDay());
        claimsRequestDTO.setFromTime(claimsRequest.getFromTime());
        claimsRequestDTO.setToTime(claimsRequest.getToTime());
        claimsRequestDTO.setTotalHours(claimsRequest.getTotalHours());
        claimsRequestDTO.setRemarks(claimsRequest.getRemarks());

        return claimsRequestDTO;

    }

    @Override
    public List<ClaimsRequestDTO> getByStaffId(Integer staffId) {

        List<ClaimsRequest> claimsRequests = claimsRequestRepository.findByStaffIdAndStatusNot(staffId, Status.STATUS_CANCELLED);
        List<ClaimsRequestDTO> claimsRequestDTOS = new ArrayList<>();
        for (ClaimsRequest claimsRequest : claimsRequests) {

            ClaimsRequestDTO claimsRequestDTO = new ClaimsRequestDTO();
            StaffDTO staffDTO = new StaffDTO();
            ProjectDTO projectDTO = new ProjectDTO();

            claimsRequestDTO.setId(claimsRequest.getId());
            claimsRequestDTO.setStatus(String.valueOf(claimsRequest.getStatus()));
            claimsRequestDTO.setDate(String.valueOf(claimsRequest.getDate()));
            claimsRequestDTO.setDay(claimsRequest.getDay());
            claimsRequestDTO.setFromTime(claimsRequest.getFromTime());
            claimsRequestDTO.setToTime(claimsRequest.getToTime());
            claimsRequestDTO.setRemarks(claimsRequest.getRemarks());

            staffDTO.setId(staffId);
            staffDTO.setName(claimsRequest.getStaff().getName());

            projectDTO.setId(claimsRequest.getProject().getId());
            projectDTO.setCode(claimsRequest.getProject().getCode());
            projectDTO.setName(claimsRequest.getProject().getName());
            projectDTO.setFromDate(String.valueOf(claimsRequest.getProject().getFromDate()));
            projectDTO.setToDate(String.valueOf(claimsRequest.getProject().getToDate()));

            claimsRequestDTO.setProjectDTO(projectDTO);
            claimsRequestDTO.setStaffDTO(staffDTO);

            claimsRequestDTOS.add(claimsRequestDTO);

        }

        return claimsRequestDTOS;

    }

    @Override
    public List<ClaimsRequestDTO> getDraftClaimRequestBystaffId(Integer staffId) {
        List<ClaimsRequest> claims = claimsRequestRepository.findByStaffIdAndStatus(staffId, Status.STATUS_DRAFT);
        List<ClaimsRequestDTO> claimDTOs = new ArrayList<>();

        for (ClaimsRequest claim : claims) {
            // Check if the staffDTOId matches the claim's staffDTOId
            if (claim.getStaffId().equals(staffId)) {
                Staff staff = staffRepository.findById(claim.getStaffId()).orElse(null);
                Project project = projectRepository.findById(claim.getProjectId()).orElse(null);

                if (staff != null && project != null) {
                    ClaimsRequestDTO claimDTO = new ClaimsRequestDTO();
                    claimDTO.setId(claim.getId());
                    claimDTO.setStatus(String.valueOf(claim.getStatus()));

                    StaffDTO staffDTO = new StaffDTO();
                    staffDTO.setId(staff.getId());
                    staffDTO.setName(staff.getName());
                    claimDTO.setStaffDTO(staffDTO);

                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setId(project.getId());
                    projectDTO.setName(project.getName());
                    projectDTO.setFromDate(String.valueOf(project.getFromDate()));
                    projectDTO.setToDate(String.valueOf(project.getToDate()));
                    claimDTO.setProjectDTO(projectDTO);

                    claimDTO.setDay(claim.getDay());
                    claimDTO.setDate(String.valueOf(claim.getDate()));
                    claimDTO.setFromTime(claim.getFromTime());
                    claimDTO.setToTime(claim.getToTime());
                    claimDTO.setTotalHours(claim.getTotalHours());
                    claimDTO.setRemarks(claim.getRemarks());

                    claimDTOs.add(claimDTO);
                }
            }
        }

        return claimDTOs;
    }

    @Override
    public List<ClaimsRequestDTO> getCancelledClaimsRequest(Integer staffId) {
        List<ClaimsRequest> claims = claimsRequestRepository.findByStaffIdAndStatus(staffId, Status.STATUS_CANCELLED);
        List<ClaimsRequestDTO> claimDTOs = new ArrayList<>();

        for (ClaimsRequest claim : claims) {
            // Check if the staffDTOId matches the claim's staffDTOId
            if (claim.getStaffId().equals(staffId)) {
                Staff staff = staffRepository.findById(claim.getStaffId()).orElse(null);
                Project project = projectRepository.findById(claim.getProjectId()).orElse(null);

                if (staff != null && project != null) {
                    ClaimsRequestDTO claimDTO = new ClaimsRequestDTO();
                    claimDTO.setId(claim.getId());
                    claimDTO.setStatus(String.valueOf(claim.getStatus()));

                    StaffDTO staffDTO = new StaffDTO();
                    staffDTO.setId(staff.getId());
                    staffDTO.setName(staff.getName());
                    claimDTO.setStaffDTO(staffDTO);

                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setId(project.getId());
                    projectDTO.setName(project.getName());
                    projectDTO.setFromDate(String.valueOf(project.getFromDate()));
                    projectDTO.setToDate(String.valueOf(project.getToDate()));
                    claimDTO.setProjectDTO(projectDTO);

                    claimDTO.setDay(claim.getDay());
                    claimDTO.setDate(String.valueOf(claim.getDate()));
                    claimDTO.setFromTime(claim.getFromTime());
                    claimDTO.setToTime(claim.getToTime());
                    claimDTO.setTotalHours(claim.getTotalHours());
                    claimDTO.setRemarks(claim.getRemarks());

                    claimDTOs.add(claimDTO);
                }
            }
        }
        return claimDTOs;
    }

    @Override
    public List<ClaimsRequestDTO> getPaidClaimRequestStaffId(Integer staffId) {
        List<ClaimsRequest> claims = claimsRequestRepository.findByStaffIdAndStatus(staffId, Status.STATUS_PAID);
        List<ClaimsRequestDTO> claimDTOs = new ArrayList<>();

        for (ClaimsRequest claim : claims) {
            // Check if the staffDTOId matches the claim's staffDTOId
            if (claim.getStaffId().equals(staffId)) {
                Staff staff = staffRepository.findById(claim.getStaffId()).orElse(null);
                Project project = projectRepository.findById(claim.getProjectId()).orElse(null);

                if (staff != null && project != null) {
                    ClaimsRequestDTO claimDTO = new ClaimsRequestDTO();
                    claimDTO.setId(claim.getId());
                    claimDTO.setStatus(String.valueOf(claim.getStatus()));

                    StaffDTO staffDTO = new StaffDTO();
                    staffDTO.setId(staff.getId());
                    staffDTO.setName(staff.getName());
                    claimDTO.setStaffDTO(staffDTO);

                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setId(project.getId());
                    projectDTO.setName(project.getName());
                    projectDTO.setFromDate(String.valueOf(project.getFromDate()));
                    projectDTO.setToDate(String.valueOf(project.getToDate()));
                    claimDTO.setProjectDTO(projectDTO);

                    claimDTO.setDay(claim.getDay());
                    claimDTO.setDate(String.valueOf(claim.getDate()));
                    claimDTO.setFromTime(claim.getFromTime());
                    claimDTO.setToTime(claim.getToTime());
                    claimDTO.setTotalHours(claim.getTotalHours());
                    claimDTO.setRemarks(claim.getRemarks());

                    claimDTOs.add(claimDTO);
                }
            }
        }
        return claimDTOs;
    }

    @Override
    public List<ClaimsRequestDTO> getPendingApprovalClaimRequestStaffId(Integer staffId) {
        List<ClaimsRequest> claims = claimsRequestRepository.findByStaffIdAndStatus(staffId, Status.STATUS_PENDING_APPROVAL);
        List<ClaimsRequestDTO> claimDTOs = new ArrayList<>();

        for (ClaimsRequest claim : claims) {
            // Check if the staffDTOId matches the claim's staffDTOId
            if (claim.getStaffId().equals(staffId)) {
                Staff staff = staffRepository.findById(claim.getStaffId()).orElse(null);
                Project project = projectRepository.findById(claim.getProjectId()).orElse(null);

                if (staff != null && project != null) {
                    ClaimsRequestDTO claimDTO = new ClaimsRequestDTO();
                    claimDTO.setId(claim.getId());
                    claimDTO.setStatus(String.valueOf(claim.getStatus()));

                    StaffDTO staffDTO = new StaffDTO();
                    staffDTO.setId(staff.getId());
                    staffDTO.setName(staff.getName());
                    claimDTO.setStaffDTO(staffDTO);

                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setId(project.getId());
                    projectDTO.setName(project.getName());
                    projectDTO.setFromDate(String.valueOf(project.getFromDate()));
                    projectDTO.setToDate(String.valueOf(project.getToDate()));
                    claimDTO.setProjectDTO(projectDTO);

                    claimDTO.setDay(claim.getDay());
                    claimDTO.setDate(String.valueOf(claim.getDate()));
                    claimDTO.setFromTime(claim.getFromTime());
                    claimDTO.setToTime(claim.getToTime());
                    claimDTO.setTotalHours(claim.getTotalHours());
                    claimDTO.setRemarks(claim.getRemarks());

                    claimDTOs.add(claimDTO);
                }
            }
        }
        return claimDTOs;
    }


    @Override
    public List<ClaimsRequestDTO> getApprovedClaimsRequestStaffId(Integer staffId) {
        List<ClaimsRequest> claims = claimsRequestRepository.findByStaffIdAndStatus(staffId, Status.STATUS_APPROVED);
        List<ClaimsRequestDTO> claimDTOs = new ArrayList<>();

        for (ClaimsRequest claim : claims) {
            // Check if the staffDTOId matches the claim's staffDTOId
            if (claim.getStaffId().equals(staffId)) {
                Staff staff = staffRepository.findById(claim.getStaffId()).orElse(null);
                Project project = projectRepository.findById(claim.getProjectId()).orElse(null);

                if (staff != null && project != null) {
                    ClaimsRequestDTO claimDTO = new ClaimsRequestDTO();
                    claimDTO.setId(claim.getId());
                    claimDTO.setStatus(String.valueOf(claim.getStatus()));

                    StaffDTO staffDTO = new StaffDTO();
                    staffDTO.setId(staff.getId());
                    staffDTO.setName(staff.getName());
                    claimDTO.setStaffDTO(staffDTO);

                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setId(project.getId());
                    projectDTO.setName(project.getName());
                    projectDTO.setFromDate(String.valueOf(project.getFromDate()));
                    projectDTO.setToDate(String.valueOf(project.getToDate()));
                    claimDTO.setProjectDTO(projectDTO);

                    claimDTO.setDay(claim.getDay());
                    claimDTO.setDate(String.valueOf(claim.getDate()));
                    claimDTO.setFromTime(claim.getFromTime());
                    claimDTO.setToTime(claim.getToTime());
                    claimDTO.setTotalHours(claim.getTotalHours());
                    claimDTO.setRemarks(claim.getRemarks());

                    claimDTOs.add(claimDTO);
                }
            }
        }
        return claimDTOs;
    }


    @Override
    public List<ClaimsRequestDTO> getAllClaimRequestStatusApproved(Integer id) {

        List<ClaimsRequestDTO> claimDTOs = new ArrayList<>();

        List<ClaimsRequest> claims = new ArrayList<>();

        List<Working> workings = workingRepository.findByStaffIdAndRoleStaff(id, Role.ROLE_FINANCE);

        for (Working working : workings) {
            List<ClaimsRequest> listElement = claimsRequestRepository.findByProjectIdAndStatus(working.getProjectId(), Status.STATUS_APPROVED);
            for (ClaimsRequest c : listElement) {
                claims.add(c);
            }
        }

        for (ClaimsRequest claim : claims) {
            // Check if the staffDTOId matches the claim's staffDTOId

            Staff staff = staffRepository.findById(claim.getStaffId()).orElse(null);
            Project project = projectRepository.findById(claim.getProjectId()).orElse(null);

            if (staff != null && project != null) {
                ClaimsRequestDTO claimDTO = new ClaimsRequestDTO();
                claimDTO.setId(claim.getId());
                claimDTO.setStatus(String.valueOf(claim.getStatus()));

                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setId(staff.getId());
                staffDTO.setName(staff.getName());
                claimDTO.setStaffDTO(staffDTO);

                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(project.getId());
                projectDTO.setName(project.getName());
                projectDTO.setFromDate(String.valueOf(project.getFromDate()));
                projectDTO.setToDate(String.valueOf(project.getToDate()));
                claimDTO.setProjectDTO(projectDTO);

                claimDTO.setDay(claim.getDay());
                claimDTO.setDate(String.valueOf(claim.getDate()));
                claimDTO.setFromTime(claim.getFromTime());
                claimDTO.setToTime(claim.getToTime());
                claimDTO.setTotalHours(claim.getTotalHours());
                claimDTO.setRemarks(claim.getRemarks());

                claimDTOs.add(claimDTO);
            }
        }

        return claimDTOs;
    }

    @Override
    public List<ClaimsRequestDTO> getAllClaimRequestStatusPaid(Integer id) {

        List<ClaimsRequestDTO> claimDTOs = new ArrayList<>();

        List<ClaimsRequest> claims = new ArrayList<>();

        List<Working> workings = workingRepository.findByStaffIdAndRoleStaff(id, Role.ROLE_FINANCE);

        for (Working working : workings) {
            List<ClaimsRequest> listElement = claimsRequestRepository.findByProjectIdAndStatus(working.getProjectId(), Status.STATUS_PAID);
            for (ClaimsRequest c : listElement) {
                claims.add(c);
            }
        }

        for (ClaimsRequest claim : claims) {
            // Check if the staffDTOId matches the claim's staffDTOId

            Staff staff = staffRepository.findById(claim.getStaffId()).orElse(null);
            Project project = projectRepository.findById(claim.getProjectId()).orElse(null);

            if (staff != null && project != null) {
                ClaimsRequestDTO claimDTO = new ClaimsRequestDTO();
                claimDTO.setId(claim.getId());
                claimDTO.setStatus(String.valueOf(claim.getStatus()));

                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setId(staff.getId());
                staffDTO.setName(staff.getName());
                claimDTO.setStaffDTO(staffDTO);

                ProjectDTO projectDTO = new ProjectDTO();
                projectDTO.setId(project.getId());
                projectDTO.setName(project.getName());
                projectDTO.setFromDate(String.valueOf(project.getFromDate()));
                projectDTO.setToDate(String.valueOf(project.getToDate()));
                claimDTO.setProjectDTO(projectDTO);

                claimDTO.setDay(claim.getDay());
                claimDTO.setDate(String.valueOf(claim.getDate()));
                claimDTO.setFromTime(claim.getFromTime());
                claimDTO.setToTime(claim.getToTime());
                claimDTO.setTotalHours(claim.getTotalHours());
                claimDTO.setRemarks(claim.getRemarks());

                claimDTOs.add(claimDTO);
            }
        }

        return claimDTOs;

    }

    public List<ClaimsRequestDTO> getAllPendingClaims() {
        List<ClaimsRequest> claimsRequestList = claimsRequestRepository.findByStatus(Status.STATUS_PENDING_APPROVAL);

        List<ClaimsRequestDTO> claimsRequestDTOList = new ArrayList<>();

        for (ClaimsRequest claimsRequest : claimsRequestList) {
            StaffDTO staffDTO = new StaffDTO();
            ProjectDTO projectDTO = new ProjectDTO();
            ClaimsRequestDTO claimsRequestDTO = new ClaimsRequestDTO();

            staffDTO.setId(claimsRequest.getStaffId());
            staffDTO.setName(claimsRequest.getStaff().getName());
            staffDTO.setDepartment(claimsRequest.getStaff().getDepartment());
            staffDTO.setRole(claimsRequest.getStaff().getRole().toString());

            projectDTO.setId(claimsRequest.getProjectId());
            projectDTO.setName(claimsRequest.getProject().getName());
            projectDTO.setFromDate(String.valueOf(claimsRequest.getProject().getFromDate()));
            projectDTO.setToDate(String.valueOf(claimsRequest.getProject().getToDate()));

            claimsRequestDTO.setStaffDTO(staffDTO);
            claimsRequestDTO.setProjectDTO(projectDTO);
            claimsRequestDTO.setId(claimsRequest.getId());
            claimsRequestDTO.setStatus(String.valueOf(claimsRequest.getStatus()));
            claimsRequestDTO.setDate(String.valueOf(claimsRequest.getDate()));
            claimsRequestDTO.setDay(claimsRequest.getDay());
            claimsRequestDTO.setFromTime(claimsRequest.getFromTime());
            claimsRequestDTO.setToTime(claimsRequest.getToTime());
            claimsRequestDTO.setTotalHours(claimsRequest.getTotalHours());
            claimsRequestDTO.setRemarks(claimsRequest.getRemarks());

            claimsRequestDTOList.add(claimsRequestDTO);
        }
        return claimsRequestDTOList;
    }

    @Override
    public List<ClaimsRequestDTO> getAllClaimsRequestByStatus(Status status) {

        List<ClaimsRequest> claims = claimsRequestRepository.findByStatus(status);
        List<ClaimsRequestDTO> claimsRequestDTOS = new ArrayList<>();

        for (ClaimsRequest claimsRequest : claims) {
            StaffDTO staffDTO = new StaffDTO();
            ProjectDTO projectDTO = new ProjectDTO();
            ClaimsRequestDTO claimsDTO = new ClaimsRequestDTO();

            staffDTO.setId(claimsRequest.getStaffId());
            staffDTO.setName(claimsRequest.getStaff().getName());
            staffDTO.setDepartment(claimsRequest.getStaff().getDepartment());
            staffDTO.setRole(claimsRequest.getStaff().getRole().toString());

            projectDTO.setId(claimsRequest.getProjectId());
            projectDTO.setName(claimsRequest.getProject().getName());
            projectDTO.setFromDate(String.valueOf(claimsRequest.getProject().getFromDate()));
            projectDTO.setToDate(String.valueOf(claimsRequest.getProject().getToDate()));

            claimsDTO.setStaffDTO(staffDTO);
            claimsDTO.setProjectDTO(projectDTO);
            claimsDTO.setId(claimsRequest.getId());
            claimsDTO.setStatus(String.valueOf(claimsRequest.getStatus()));
            claimsDTO.setDate(String.valueOf(claimsRequest.getDate()));
            claimsDTO.setDay(claimsRequest.getDay());
            claimsDTO.setFromTime(claimsRequest.getFromTime());
            claimsDTO.setToTime(claimsRequest.getToTime());
            claimsDTO.setTotalHours(claimsRequest.getTotalHours());
            claimsDTO.setRemarks(claimsRequest.getRemarks());

            claimsRequestDTOS.add(claimsDTO);
        }
        return claimsRequestDTOS;
    }
}
