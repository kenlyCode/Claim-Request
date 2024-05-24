package com.vn.dto;

import com.vn.entities.Project;
import com.vn.entities.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimsRequestDTO {

    private Integer id;
    private String status;//NOT NULL (Draft, Pending_Approval, Approved, Paid, Rejected or Cancelled)
    private String date;//NOT NULL (between fromDate and toDate of Project)
    private String day;//(Example: MON, TUE)
    private String fromTime;//NOT NULL (User selects start time of actual working hours)
    private String toTime;//NOT NULL (User selects end time of actual working hours)
    private Double totalHours;//NOT NULL (User input total working hour)
    private String remarks;//(Creator inputs remarks for each record)
    private StaffDTO staffDTO;
    private ProjectDTO projectDTO;
    
}
