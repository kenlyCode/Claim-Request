package com.vn.api;

import com.vn.auth.CustomStaffDetail;
import com.vn.dto.StaffDTO;
import com.vn.dto.StaffProjectDTO;
import com.vn.repository.StaffRepository;
import com.vn.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StaffControllerAPI {

    @Autowired
    StaffService staffService;

    @Autowired
    StaffRepository staffRepository;

    @GetMapping("api/staff/{staffId}/employee")
    public StaffProjectDTO getInfomationStaff(@PathVariable("staffId") Integer staffId) {
        return staffService.getInfomationStaff(staffId);
    }

    @GetMapping("api/staff/{staffId}/admin")
    public StaffDTO showStaffInformation(@PathVariable("staffId") Integer staffId) {
        return staffService.showStaffInformation(staffId);
    }

    @GetMapping("/api/staff/admin")
    public List<StaffDTO> getAllStaffDTO() {
        return staffService.getAllStaffDTO();
    }

    @GetMapping("/api/current-user")
    public ResponseEntity<?> getCurrentUser() {
        CustomStaffDetail customStaffDetail = (CustomStaffDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Trích xuất ID của nhân viên từ thông tin người dùng
        Integer staffId = customStaffDetail.getStaff().getId();
        return ResponseEntity.ok(staffId);
    }
}
