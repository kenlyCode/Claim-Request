package com.vn.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer id;

    private String name;//NOT NULL UNIQUE
    private String code;//(Max length: 20 characters)
    private Date fromDate;//NOT NULL
    private Date toDate;//NOT NULL (toDate >= fromDate)

    @OneToMany(mappedBy = "project")
    private List<Working> workings = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<ClaimsRequest> claimsRequests = new ArrayList<>();

}