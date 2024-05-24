package com.vn.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    String subject;
    String from;
    String body;
    String to;
    Object data;

}
