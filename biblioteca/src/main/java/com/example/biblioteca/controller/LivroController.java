package com.example.biblioteca.controller;

import com.example.biblioteca.model.entity.Livro;
import com.example.biblioteca.model.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @PostMapping
    public Livro adicionarLivro(@RequestBody Livro livro) {
        return livroRepository.save(livro);
    }

    @GetMapping
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setAutor(livroAtualizado.getAutor());
                    livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
                    Livro livroSalvo = livroRepository.save(livro);
                    return ResponseEntity.ok(livroSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarLivro(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livroRepository.delete(livro);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

