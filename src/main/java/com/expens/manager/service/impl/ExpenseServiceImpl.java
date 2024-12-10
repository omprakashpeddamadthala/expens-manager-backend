package com.expens.manager.service.impl;


import com.expens.manager.dto.ExpenseDTO;
import com.expens.manager.entity.ExpenseEntity;
import com.expens.manager.repository.ExpenseRepository;
import com.expens.manager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        return List.of();
    }
}
