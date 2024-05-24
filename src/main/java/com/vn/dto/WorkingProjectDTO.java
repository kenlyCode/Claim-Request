package com.vn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingProjectDTO {

    private Integer id;//id of Working

    private String name;//name of Project

    private String role;//role of Staff in Project

    private String fromDate;

    private String toDate;

    private Integer projectId;//id of Project
}
