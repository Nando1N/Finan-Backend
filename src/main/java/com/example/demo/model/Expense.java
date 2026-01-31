package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@Data
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description; //descrição

    private BigDecimal amount; //valor

    @Column(name = "due_date")
    private LocalDate dueDate; //data de vencimento/fatura

    private Boolean paid = false; //definir sempre que é como não pago

    private Integer currentInstallment; //parcela 01
    private Integer totalInstallments; // total de parcelas

    @Column(name = "transaction_group_id")
    private UUID transactionGroupId; // Esse ID vai linkar todas as parcelas geradas, servirá para quando apagar esse gasto, apagar todas as parcelas, atualizar...


    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private UUID userId; //
}

