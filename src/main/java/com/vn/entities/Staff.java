package com.vn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer id;

    private String name;//NOT NULL
    private String department;//NOT NULL
    private String rank;//NOT NULL (short of Job Rank)
    private Double salary;//NOT NULL
    private String password;//NOT NULL
    private String rePassword;//NOT NULL (DATABASE: Entity Staff is not necessary rePassword but i want that)
    private String email;//NOT NULL, UNIQUE

    @Enumerated(EnumType.STRING)
    private Status status;//STATUS_ALLOCATED or STATUS_UNALLOCATED

    //(STARTSUP: admin: ROLE_ADMIN, finance(finance staff): ROLE_FINANCE, staff: NULL)
    //(admin has created Project: Staff will have role if Staff is joining Project)
    //(admin delete Project.staff: Staff will have role is NULL and status is STATUS_UNALLOCATED
    @Enumerated(EnumType.STRING)
    private Role role;//role current is not role in each Project

    @JsonIgnore
    @OneToMany(mappedBy = "staff")
    private List<Working> workings = new ArrayList<>();

    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private List<ClaimsRequest> claimsRequests = new ArrayList<>();

}
