package com.vn.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Working {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;//NOT NULL

    @Column(name = "staff_id", insertable = false, updatable = false)
    private Integer staffId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;//NOT NULL

    @Column(name = "project_id", insertable = false, updatable = false)
    private Integer projectId;


    @Column(name = "role_staff")
    @Enumerated(EnumType.STRING)
    private Role roleStaff;//role of Staff

}