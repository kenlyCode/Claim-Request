package com.vn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffProjectDTO {

    private StaffDTO staffDTO;
    private List<WorkingProjectDTO> workingProjectDTOS;

}
