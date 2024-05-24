package com.vn.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {

    private Integer id;
    private String name;//NOT NULL UNIQUE
    private String code;//(Max length: 20 characters)
    private String fromDate;//NOT NULL
    private String toDate;//NOT NULL (toDate >= fromDate)

}
