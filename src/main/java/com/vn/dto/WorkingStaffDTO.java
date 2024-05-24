package com.vn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingStaffDTO {

    private String name;//NOT NULL, name of Staff(i think it is unique is reasonable)
    private String status;//status of Staff (it is not necessary)
    private String roleStaff;//role of Staff

}
