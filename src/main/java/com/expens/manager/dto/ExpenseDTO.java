package com.expens.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseDTO implements Serializable {
    private String expenseId;
    private String name;
    private String note;
    private String category;
    private Date date;
    private BigDecimal amount;
}
