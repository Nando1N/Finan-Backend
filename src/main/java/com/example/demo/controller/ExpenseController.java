package com.example.demo.controller;

import com.example.demo.model.Expense;
import com.example.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/parcelar")
    public String criarParcelas(
            @RequestBody Expense despesa,
            @AuthenticationPrincipal Jwt jwt // 👈 Aqui o Java captura o Token automaticamente
    ) {
        // O campo "sub" no token do Supabase contém o ID único do usuário
        String userIdLogado = jwt.getClaim("sub");

        // Agora passamos o ID do usuário diretamente para o Service
        expenseService.criarDespesaParcelado(despesa, userIdLogado);

        return "Despesas vinculadas ao usuário " + userIdLogado + " criadas com sucesso!";
    }
}