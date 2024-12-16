package com.expens.manager.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorObject {
    private String message;
    private String errorCode;
    private Integer statusCode;
    private Date timestamp;
}


