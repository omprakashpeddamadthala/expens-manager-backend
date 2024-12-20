package com.expens.manager.controller;

import com.expens.manager.dto.ExpenseDTO;
import com.expens.manager.io.ExpenseResponse;
import com.expens.manager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

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
     * It will fetch expense details by expenseId from database
     * @param expenseId
     * @return expense details
     * */
    @GetMapping("/expenses/{expenseId}")
    public ExpenseResponse getExpenseByExpenseId(@PathVariable String expenseId) {
        log.info( "API Get /expenses/" + expenseId + " called successfully" );
        ExpenseDTO expenseDTO = expenseService.getExpenseByExpenseId( expenseId );
        return mapToExpenseResponse( expenseDTO);
    }

    /**
     * It will delete expense details by expenseId from database
     * @param expenseId
     * */
    @DeleteMapping("/expenses/{expenseId}")
    public void deleteExpenseByExpenseId(@PathVariable String expenseId){
        log.info( "API Delete /expenses/" + expenseId + " called successfully" );
        expenseService.deleteExpenseByExpenseId( expenseId );
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
