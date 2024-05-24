package com.vn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDTO {

    private Integer id;
    private StaffDTO staffDTO;
    private ProjectDTO projectDTO;
    private String roleStaff;

}
