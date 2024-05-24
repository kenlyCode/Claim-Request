package com.vn.service;

import com.vn.dto.StaffDTO;
import com.vn.dto.StaffProjectDTO;
import com.vn.entities.Staff;

import com.vn.entities.Staff;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.List;

public interface StaffService {
    Staff createCRUD(StaffDTO staffDTO);

    Staff updateCRUD(StaffDTO staffDTO);

    List<StaffDTO> getAllStaffDTO();

    StaffDTO showStaffInformation(Integer staffId);

    StaffProjectDTO getInfomationStaffUI(Integer staffId);

    Boolean checkExistEmailAndPassword(StaffDTO staffDTO);

    StaffProjectDTO getInfomationStaff(Integer staffId);

    boolean checkExistEmail(String email);

    boolean checkExistName(String name);

}
