package com.vn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class ClaimsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Status status;//NOT NULL (Draft, Pending_Approval, Approved, Paid, Rejected or Cancelled)
    
    private Date date;//NOT NULL (between fromDate and toDate of Project)
    private String day;//(Example: MON, TUE)
    private String fromTime;//NOT NULL (User selects start time of actual working hours)
    private String toTime;//NOT NULL (User selects end time of actual working hours)
    private Double totalHours;//NOT NULL (User input total working hour)
    private String remarks;//(Creator inputs remarks for each record)

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(name = "staff_id", insertable = false, updatable = false)
    private Integer staffId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "project_id", insertable = false, updatable = false)
    private Integer projectId;

}
