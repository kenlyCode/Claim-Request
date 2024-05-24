package com.vn.api;

import com.vn.dto.ProjectDTO;
import com.vn.dto.ProjectStaffDTO;
import com.vn.dto.WorkingStaffDTO;
import com.vn.entities.Project;
import com.vn.entities.Staff;
import com.vn.entities.Working;
import com.vn.service.ProjectServiceImpl;
import com.vn.service.StaffService;
import com.vn.service.StaffServiceImpl;
import com.vn.service.WorkingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectControllerAPI {

    @Autowired
    ProjectServiceImpl projectService;

    @Autowired
    StaffService staffService;

    @Autowired
    WorkingServiceImpl workingService;

//    @PostMapping("/api/createProject")
//    public ResponseEntity<String> insertProjectAndStaff(@RequestBody ProjectStaffDTO projectStaffDTO) {
//
//        ProjectDTO projectDTO = projectStaffDTO.getProjectDTO();
//        Project project = projectService.create(projectDTO);
//        List<WorkingStaffDTO> workingStaffDTOS = projectStaffDTO.getWorkingStaffDTOS();
//
//        for (WorkingStaffDTO workingStaffDTO : workingStaffDTOS) {
//
//            Staff staff = staffService.update(workingStaffDTO);//Find staff by name in WorkingStaffDTO
//
//            Working working = new Working();
//            working.setProject(project);
//            working.setStaff(staff);
//            workingService.create(working);
//
//        }
//
//        return ResponseEntity.ok("Project inserted successfully");
//    }

}
