package com.expens.manager.repository;

import com.expens.manager.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Serializable> {
}
