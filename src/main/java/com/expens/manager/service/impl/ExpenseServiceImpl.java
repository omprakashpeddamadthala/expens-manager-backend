package com.expens.manager.service.impl;


import com.expens.manager.dto.ExpenseDTO;
import com.expens.manager.entity.ExpenseEntity;
import com.expens.manager.expection.ResourceNotFoundException;
import com.expens.manager.repository.ExpenseRepository;
import com.expens.manager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for expense module
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-12-10
 * */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final ModelMapper modelMapper;

    /**
     * It will fetch expense from database
     * @return list of expense
     * */
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        List<ExpenseEntity> expenseEntityList = expenseRepository.findAll();
        log.info( "printing data from database expenseEntityList {}",expenseEntityList );
        List<ExpenseDTO> expenseDTOListList =expenseEntityList.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).collect( Collectors.toList());
        return expenseDTOListList;
    }

    /**
     * It will fetch single expense details  from database
     * @param expenseId
     * @return expenseDTO
     * */
    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = expenseRepository.findByExpenseId( expenseId ).orElseThrow( () -> new ResourceNotFoundException( "Expense details not found for expense id "+expenseId ) );
        log.info( "printing data from database expenseEntity {}",expenseEntity );
        return mapToExpenseDTO( expenseEntity);
    }

    /**
     * Mapper method for converting from entity object to dto object
     * @param expenseEntity
     * @return expenseDTO
     * */
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
       return modelMapper.map(expenseEntity,ExpenseDTO.class );
    }
}
