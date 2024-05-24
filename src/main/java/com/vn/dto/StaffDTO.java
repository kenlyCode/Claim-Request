package com.vn.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private Integer id;
    @NotBlank(message = "Please specify value for this field.")
    private String name;//NOT NULL
    @NotBlank(message = "Please specify value for this field.")
    private String department;//NOT NULL
    @NotBlank(message = "Please specify value for this field.")
    private String rank;//NOT NULL (short of Job Rank)
    @NotBlank(message = "Please specify value for this field.")
    private Double salary;//NOT NULL
    private String password;//NOT NULL
    private String rePassword;//NOT NULL (DATABASE: Entity Staff is not necessary rePassword but i want that)
    private String email;//NOT NULL, UNIQUE
    private String status;//STATUS_ALLOCATED or STATUS_UNALLOCATED
    private String role;//may be role current or role in special Project, it is not affect to database

}
