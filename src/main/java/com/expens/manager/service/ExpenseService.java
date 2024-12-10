package com.expens.manager.service;

import com.expens.manager.dto.ExpenseDTO;
import com.expens.manager.entity.ExpenseEntity;

import java.util.List;

public interface ExpenseService {

    List<ExpenseDTO> getAllExpenses();
}
