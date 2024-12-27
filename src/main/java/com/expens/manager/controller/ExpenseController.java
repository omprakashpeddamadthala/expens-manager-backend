package com.expens.manager.controller;

import com.expens.manager.dto.ExpenseDTO;
import com.expens.manager.io.ExpenseRequest;
import com.expens.manager.io.ExpenseResponse;
import com.expens.manager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
     * It will save expense details to database
     * @param expenseRequest
     * @return expense response
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ExpenseResponse saveExpenseDetails(@RequestBody ExpenseRequest expenseRequest) {
        log.info("API Post /expenses called successfully {} ",expenseRequest);
        ExpenseDTO expenseDTO =expenseService.saveExpenseDetails(mapToExpenseDto(expenseRequest) );
        log.info( "Printing data  expenseDTO : {}", expenseDTO );
        return  mapToExpenseResponse( expenseDTO );
    }

    /**
     * It will update  expense details to database
     * @param updateRequest
     * @return expense response
     * */
    @PutMapping(("/expenses/{expenseId}"))
    public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest updateRequest ,@PathVariable String expenseId ){
        log.info("API PUT /expenses called successfully {} ",updateRequest);
        ExpenseDTO updateExpenseDTO = mapToExpenseDto( updateRequest );
        updateExpenseDTO =expenseService.updateExpenseDetails( updateExpenseDTO , expenseId);
        return mapToExpenseResponse( updateExpenseDTO );
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
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

    /**
     * Mapper method for converting from expense request  object to dto object
     * @param expenseRequest
     * @return ExpenseDTO
     * */
    private ExpenseDTO mapToExpenseDto(ExpenseRequest expenseRequest) {
        return modelMapper.map( expenseRequest, ExpenseDTO.class);
    }
}
