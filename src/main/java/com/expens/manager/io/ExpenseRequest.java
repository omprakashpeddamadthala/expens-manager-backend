package com.expens.manager.io;

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
public class ExpenseRequest implements Serializable {
    private String name;
    private String note;
    private String category;
    private Date  date;
    private BigDecimal amount;
}
