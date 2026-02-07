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
            @AuthenticationPrincipal Jwt jwt
    ) {
        String userIdLogado;

        // Se o token for nulo (teste sem login), usamos o seu ID real do banco
        if (jwt == null) {
            userIdLogado = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11";
            System.out.println("LOG: Usando ID de teste: " + userIdLogado);
        } else {
            userIdLogado = jwt.getClaim("sub");
        }

        // Agora o Service vai encontrar o usuário a0eeb... no banco e salvar!
        expenseService.criarDespesaParcelado(despesa, userIdLogado);

        return "Sucesso! Parcelas criadas para o usuário: " + userIdLogado;
    }
}