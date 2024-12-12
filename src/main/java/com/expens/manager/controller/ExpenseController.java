package com.expens.manager.controller;

import com.expens.manager.dto.ExpenseDTO;
import com.expens.manager.io.ExpenseResponse;
import com.expens.manager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for Expense module
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-12-10
 * */
@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseService expenseService;

    private final ModelMapper modelMapper;

    /**
     * It will fetch expense from database
     * @return list of expense
     * */
    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses(){
        log.info("API Get /expenses called successfully");
        List<ExpenseDTO> expenseDTOList=expenseService.getAllExpenses();
        log.info("Printing data from service expenseDTOList : {}", expenseDTOList);
        List<ExpenseResponse> expenseResponseList=expenseDTOList.stream()
                .map( expenseDTO -> mapToExpenseResponse(expenseDTO)).collect( Collectors.toList());
        return expenseResponseList;
    }

    /**
     * Mapper method for converting from dto object to response object
     * @param expenseDTO
     * @return ExpenseResponse
     * */
    private ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO) {
       return modelMapper.map( expenseDTO, ExpenseResponse.class);
    }
}
