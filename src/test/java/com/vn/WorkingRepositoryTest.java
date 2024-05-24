package com.vn;

import com.vn.dto.ClaimsRequestDTO;
import com.vn.dto.ProjectDTO;
import com.vn.dto.StaffDTO;
import com.vn.entities.*;
import com.vn.repository.ClaimsRequestRepository;
import com.vn.repository.ProjectRepository;
import com.vn.repository.StaffRepository;
import com.vn.repository.WorkingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WorkingRepositoryTest {

    @Autowired
    WorkingRepository workingRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ClaimsRequestRepository claimsRequestRepository;

    @Test
    void WorkingRepository_TC001_success() {

        Working working = new Working();
        Staff staff = staffRepository.findById(5).orElse(null);
        Project project = projectRepository.findById(1).orElse(null);
        working.setStaff(staff);
        working.setProject(project);
        working.setRoleStaff(Role.ROLE_DEVELOPER);
        workingRepository.save(working);

    }

    @Test
    void WorkingRepository_TC002_findByStaffIdAndProjectId_success() {

        Working working = workingRepository.findByStaffIdAndProjectId(3, 1);
        System.out.println(working.getRoleStaff());
        Assertions.assertNotNull(working);

    }
    @Test
    void WorkingRepository_TC003_findByStaffIdAndProjectId_success() {
        ClaimsRequest claimsRequest = claimsRequestRepository.findById(8).orElse(null);
        System.out.println(claimsRequest);

        Integer staffId = claimsRequest.getStaffId();
        Integer projectId = claimsRequest.getProjectId();

        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setRole(String.valueOf(workingRepository.findByStaffIdAndProjectId(staffId, projectId).getRoleStaff()));

        System.out.println(staffDTO.getRole());
    }

}
