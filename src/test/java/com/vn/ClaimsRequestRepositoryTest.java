package com.vn;

import com.vn.entities.*;
import com.vn.repository.ClaimsRequestRepository;
import com.vn.repository.ProjectRepository;
import com.vn.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ClaimsRequestRepositoryTest {

    @Autowired
    ClaimsRequestRepository claimsRequestRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void ClaimsRequestRepository_TC001_success() throws ParseException {

        ClaimsRequest claimsRequest = new ClaimsRequest();
        claimsRequest.setStatus(Status.STATUS_DRAFT);
        claimsRequest.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-05-01"));
        claimsRequest.setDay("MON");//Calculate day by date based on Javascript
        claimsRequest.setFromTime("10:00");
        claimsRequest.setToTime("11:30");
        claimsRequest.setTotalHours(1.5);
        claimsRequest.setRemarks(null);
        Staff staff = staffRepository.findById(3).orElse(null);
        Project project = projectRepository.findById(2).orElse(null);
        claimsRequest.setStaff(staff);
        claimsRequest.setProject(project);
        claimsRequestRepository.save(claimsRequest);

    }

    @Test
    void ViewAllVetting_TC001() throws ParseException{
        List<ClaimsRequest> claimsRequestList = claimsRequestRepository.findByStatus(Status.STATUS_DRAFT);
        System.out.println(claimsRequestList.size());
    }

}
