package com.expens.manager.repository;

import com.expens.manager.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Serializable> {

    Optional<ExpenseEntity> findByExpenseIdAndOwnerId(String expenseId,Long ownerId);

    List<ExpenseEntity> findAllByOwnerId(Long ownerId);

}
