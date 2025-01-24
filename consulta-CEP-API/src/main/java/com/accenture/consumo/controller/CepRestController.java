package com.accenture.consumo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accenture.consumo.model.Endereco;
import com.accenture.consumo.service.CepService;
import com.accenture.consumo.service.EnderecoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") 
public class CepRestController {

    @Autowired
    private CepService cepService;

    @Autowired
    private EnderecoService enderecoService;

    @GetMapping("/{cep}")
    public ResponseEntity<Endereco> getCep(@PathVariable String cep) {
        Endereco endereco = cepService.buscaEnderecoPorCep(cep);

        if (endereco != null) {
            enderecoService.salvarEndereco(endereco); // Salvar no banco de dados
            return ResponseEntity.ok(endereco);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

