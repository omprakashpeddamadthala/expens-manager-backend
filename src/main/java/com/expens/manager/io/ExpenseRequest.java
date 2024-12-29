package com.expens.manager.io;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Expense name is required")
    @Size(min = 2 ,message = "Expense name should be at least 3 characters")
    private String name;

    private String note;

    @NotNull(message = "Expense category is required")
    private String category;

    @NotNull(message = "Expense date is required")
    private Date  date;

    @NotNull(message = "Expense amount is required")
    private BigDecimal amount;
}
