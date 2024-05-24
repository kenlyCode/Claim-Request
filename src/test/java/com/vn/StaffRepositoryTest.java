package com.vn;

import com.vn.dto.StaffDTO;
import com.vn.entities.Staff;
import com.vn.entities.Status;
import com.vn.repository.StaffRepository;
import com.vn.service.StaffService;
import com.vn.service.StaffServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StaffRepositoryTest {

    @Autowired
    StaffRepository staffRepository;
    @Autowired
    StaffService staffService;

    @Test
    void StaffRepositoryTest_TC001_success() {
        Staff staff = new Staff();
        staff.setName("Huy Gau 1");
        staff.setDepartment("FSA");
        staff.setRank("Trainer");
        staff.setSalary(50000.00d);
        staff.setPassword("huy01");
        staff.setRePassword("huy01");
        staff.setEmail("huypn2_V3@fpt.com");
        staff.setStatus(Status.STATUS_UNALLOCATED);
        staff.setRole(null);
        staffRepository.save(staff);

    }

    @Test
    void StaffRepositoryTest_TC002_update_success() {

        Staff staff = new Staff();
        staff.setId(11);
        staff.setName("Huy Gau 02");
        staff.setDepartment("FSA");
        staff.setRank("Trainer");
        staff.setSalary(50000.00d);
        staff.setPassword("huy01");
        staff.setRePassword("huy01");
        staff.setEmail("ABC.com");
        staff.setStatus(Status.STATUS_UNALLOCATED);
        staff.setRole(null);
        staffRepository.save(staff);

    }
}
