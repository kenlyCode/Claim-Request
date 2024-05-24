package com.vn.service;

import com.vn.dto.StaffDTO;
import com.vn.dto.StaffProjectDTO;
import com.vn.dto.WorkingProjectDTO;
import com.vn.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.vn.entities.Staff;
import com.vn.entities.Role;
import com.vn.entities.Status;
import com.vn.entities.Working;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<StaffDTO> getAllStaffDTO() {
        List<Staff> staffs = staffRepository.findAll();
        List<StaffDTO> staffDTOS = new ArrayList<>();
        for (Staff staff : staffs) {
            StaffDTO staffDTO = new StaffDTO();

            staffDTO.setId(staff.getId());
            staffDTO.setName(staff.getName());
            staffDTO.setDepartment(staff.getDepartment());
            staffDTO.setRank(staff.getRank());
            staffDTO.setEmail(staff.getEmail());

            staffDTOS.add(staffDTO);
        }
        return staffDTOS;
    }

    @Override
    public StaffDTO showStaffInformation(Integer staffId) {
        Staff staff = staffRepository.findById(staffId).orElse(null);

        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setId(staffId);
        staffDTO.setName(staff.getName());
        staffDTO.setDepartment(staff.getDepartment());
        staffDTO.setRank(staff.getRank());
        staffDTO.setSalary(staff.getSalary());
        staffDTO.setEmail(staff.getEmail());
        staffDTO.setPassword(staff.getPassword());

        return staffDTO;
    }

    @Override
    public StaffProjectDTO getInfomationStaff(Integer staffId) {
        StaffProjectDTO staffProjectDTO = new StaffProjectDTO();

        Staff staff = staffRepository.findById(staffId).orElse(null);
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setName(staff.getName());
        staffDTO.setDepartment(staff.getDepartment());
        staffDTO.setId(staff.getId());
        staffProjectDTO.setStaffDTO(staffDTO);

        List<Working> workings = staff.getWorkings();
        List<WorkingProjectDTO> workingProjectDTOS = new ArrayList<>();

        for (Working working : workings) {
            WorkingProjectDTO workingProjectDTO = new WorkingProjectDTO();
            workingProjectDTO.setId(working.getId());
            workingProjectDTO.setName(working.getProject().getName());
            workingProjectDTO.setRole(String.valueOf(working.getRoleStaff()));
            workingProjectDTO.setFromDate(String.valueOf(working.getProject().getFromDate()));
            workingProjectDTO.setToDate(String.valueOf(working.getProject().getToDate()));
            workingProjectDTO.setProjectId(working.getProjectId());
            workingProjectDTOS.add(workingProjectDTO);
        }

        staffProjectDTO.setWorkingProjectDTOS(workingProjectDTOS);

        return staffProjectDTO;
    }

    @Override
    public StaffProjectDTO getInfomationStaffUI(Integer staffId) {
        StaffProjectDTO staffProjectDTO = new StaffProjectDTO();

        Staff staff = staffRepository.findById(staffId).orElse(null);
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setName(staff.getName());
        staffDTO.setDepartment(staff.getDepartment());
        staffDTO.setId(staff.getId());
        staffProjectDTO.setStaffDTO(staffDTO);

        List<Working> workings = staff.getWorkings();
        List<WorkingProjectDTO> workingProjectDTOS = new ArrayList<>();

        for (Working working : workings) {
            WorkingProjectDTO workingProjectDTO = new WorkingProjectDTO();
            workingProjectDTO.setId(working.getId());
            workingProjectDTO.setName(working.getProject().getName());
            workingProjectDTO.setRole(String.valueOf(working.getRoleStaff()));
            workingProjectDTO.setFromDate(String.valueOf(working.getProject().getFromDate()));
            workingProjectDTO.setToDate(String.valueOf(working.getProject().getToDate()));
            workingProjectDTO.setProjectId(working.getProjectId());
            workingProjectDTOS.add(workingProjectDTO);
        }

        staffProjectDTO.setWorkingProjectDTOS(workingProjectDTOS);

        return staffProjectDTO;
    }

    @Override
    public boolean checkExistEmail(String email) {
        Staff staff = staffRepository.findByEmail(email);
        return staff != null;
    }

    @Override
    public boolean checkExistName(String name) {
        Staff staff = staffRepository.findByName(name);
        return staff != null;
    }

    @Override
    public Staff createCRUD(StaffDTO staffDTO) {
        Staff staff = Staff.builder()
                .name(staffDTO.getName())
                .department(staffDTO.getDepartment())
                .rank(staffDTO.getRank())
                .salary(staffDTO.getSalary())
                .status(Status.STATUS_UNALLOCATED)
                .email(staffDTO.getEmail())
                .password(passwordEncoder.encode(staffDTO.getPassword()))
                .rePassword(passwordEncoder.encode(staffDTO.getRePassword()))
                .build();

        if (staffDTO.getRank().equalsIgnoreCase("Admin")) {
            staff.setRole(Role.ROLE_ADMIN);
        } else if (staffDTO.getRank().equalsIgnoreCase("Finance")) {
            staff.setRole(Role.ROLE_FINANCE);
        } else {
            staff.setRole(Role.ROLE_EMPLOYEE);
        }
        staffRepository.save(staff);
        return staff;
    }

    @Override
    public Staff updateCRUD(StaffDTO staffDTO) {
        Staff staff = staffRepository.findByEmail(staffDTO.getEmail());

        if (!staffDTO.getName().isBlank()) {
            staff.setName(staffDTO.getName());
        }
        if (!staffDTO.getDepartment().isBlank()) {
            staff.setDepartment(staffDTO.getDepartment());
        }
        if (!staffDTO.getSalary().toString().isBlank()) {
            staff.setSalary(staffDTO.getSalary());
        }
        if (!staffDTO.getPassword().isBlank()) {
            staff.setPassword(passwordEncoder.encode(staffDTO.getPassword()));
        }
        if (!staffDTO.getRePassword().isBlank()) {
            staff.setRePassword(passwordEncoder.encode(staffDTO.getRePassword()));
        }
        if (!staffDTO.getRank().isBlank() || staffDTO.getRank().equalsIgnoreCase("sameAsBefore")) {
            staff.setRank(staffDTO.getRank());
        }

        staffRepository.save(staff);
        return staff;
    }

    @Override
    public Boolean checkExistEmailAndPassword(StaffDTO staffDTO) {
        Staff Staff = staffRepository.findByEmailAndPassword(staffDTO.getEmail(), staffDTO.getPassword());
        return Staff != null;
    }
}
