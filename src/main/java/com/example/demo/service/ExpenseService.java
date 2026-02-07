package com.example.demo.service;

import com.example.demo.model.Expense;
import com.example.demo.model.User;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // O import que estava faltando!
import java.util.UUID;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    public void criarDespesaParcelado(Expense despesaBase, String userId) {
        UUID groupId = UUID.randomUUID();
        int total = despesaBase.getTotalInstallments();

        // Buscamos o usuário real no banco
        User usuarioReal = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        for (int i = 1; i <= total; i++) {
            Expense parcela = new Expense();
            parcela.setDescription(despesaBase.getDescription());
            parcela.setAmount(despesaBase.getAmount());
            parcela.setUser(usuarioReal); // Vincula o objeto User
            parcela.setTransactionGroupId(groupId);
            parcela.setCurrentInstallment(i);
            parcela.setTotalInstallments(total);
            parcela.setDueDate(despesaBase.getDueDate().plusMonths(i - 1));

            expenseRepository.save(parcela);
        }
    }
}