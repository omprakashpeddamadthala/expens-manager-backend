package com.expens.manager.service;

import com.expens.manager.dto.ExpenseDTO;
import com.expens.manager.entity.ExpenseEntity;

import java.util.List;
/**
 * Service interface for expense module
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-12-10
 * */
public interface ExpenseService {

    List<ExpenseDTO> getAllExpenses();

    ExpenseDTO getExpenseByExpenseId(String expenseId);

    void deleteExpenseByExpenseId(String expenseId);

    ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO);
}
