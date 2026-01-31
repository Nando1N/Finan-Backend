package com.example.demo.repository;

import com.example.demo.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByUserId(UUID userId);

    List<Expense> findByPaid(Boolean paid);
}
