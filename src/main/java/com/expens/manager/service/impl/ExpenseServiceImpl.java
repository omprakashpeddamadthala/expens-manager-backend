package com.expens.manager.service.impl;


import com.expens.manager.dto.ExpenseDTO;
import com.expens.manager.entity.ExpenseEntity;
import com.expens.manager.expection.ResourceNotFoundException;
import com.expens.manager.repository.ExpenseRepository;
import com.expens.manager.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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
     * It will save expense details to database
     * @return list of expense
     * */
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO){
        ExpenseEntity newExpenseEntity = this.mapToExpenseEntity( expenseDTO );
        newExpenseEntity.setExpenseId( UUID.randomUUID().toString() );
        newExpenseEntity=expenseRepository.save( newExpenseEntity );
        log.info( "expense details saved successfully for expenseId {}",newExpenseEntity.getExpenseId() );
        return mapToExpenseDTO( newExpenseEntity );
    }
    /**
     * It will update expense details to database
     * @return list of expense dto
     * */
    @Override
    public ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO, String expenseId) {
        ExpenseEntity existingExpense = this.getExpenseEntity( expenseId );
        ExpenseEntity updateExpenseEntity = mapToExpenseEntity( expenseDTO );
        updateExpenseEntity.setId( existingExpense.getId());
        updateExpenseEntity.setExpenseId( existingExpense.getExpenseId());
        updateExpenseEntity.setCreatedAt( existingExpense.getCreatedAt() );
        updateExpenseEntity.setUpdatedAt( existingExpense.getUpdatedAt() );
        ExpenseEntity savedExpenseEntity = expenseRepository.save( updateExpenseEntity );
        log.info( "Printing the updated expense entity details {} ",updateExpenseEntity );
        return mapToExpenseDTO( savedExpenseEntity );
    }

    /**
     * It will fetch single expense details  from database
     * @param expenseId
     * @return expenseDTO
     * */
    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = this.getExpenseEntity( expenseId );
        log.info( "printing data from database expenseEntity {}",expenseEntity );
        return mapToExpenseDTO( expenseEntity);
    }

    /**
     * It will delete expense details from database by expenseId
     * @param expenseId
     * */
    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = this.getExpenseEntity( expenseId );
        expenseRepository.delete( expenseEntity );
        log.info( "expense details deleted successfully for expenseId {}",expenseId );
    }

    /**
     * Mapper method for converting from dto object to entity object
     * @param expenseDTO
     * @return expenseEntity
     * */
    private ExpenseEntity mapToExpenseEntity(ExpenseDTO expenseDTO) {
       return modelMapper.map(expenseDTO,ExpenseEntity.class );
    }

    /**
     * Mapper method for converting from entity object to dto object
     * @param expenseEntity
     * @return expenseDTO
     * */
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
       return modelMapper.map(expenseEntity,ExpenseDTO.class );
    }

    /**
     * It will fetch single expense entity  from database
     * @param expenseId
     * @return expenseEntity
     * */
    private ExpenseEntity getExpenseEntity(String expenseId) {
        return expenseRepository.findByExpenseId( expenseId ).
                orElseThrow( () -> new ResourceNotFoundException( "Expense details not found for expense id " + expenseId ) );
    }
}
