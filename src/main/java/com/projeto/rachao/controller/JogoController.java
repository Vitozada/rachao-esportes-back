package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Jogo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jogos")
@CrossOrigin(origins = "http://localhost:4200")
public class JogoController {

    @PersistenceContext
    private EntityManager entityManager;

    // Lista todos os jogos cadastrados.
    @GetMapping
    public List<Jogo> listar() {
        return entityManager
                .createQuery("select j from Jogo j", Jogo.class)
                .getResultList();
    }

    // Busca um jogo pelo identificador.
    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscar(@PathVariable Long id) {
        Jogo jogo = entityManager.find(Jogo.class, id);
        return jogo == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(jogo);
    }

    // Cria um novo jogo.
    @PostMapping
    @Transactional
    public ResponseEntity<Jogo> criar(@RequestBody Jogo jogo) {
        entityManager.persist(jogo);
        return ResponseEntity.status(201).body(jogo);
    }

    // Atualiza um jogo existente.
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Jogo> atualizar(
            @PathVariable Long id,
            @RequestBody Jogo jogo
    ) {
        if (entityManager.find(Jogo.class, id) == null) {
            return ResponseEntity.notFound().build();
        }

        jogo.setId(id);
        return ResponseEntity.ok(entityManager.merge(jogo));
    }

    // Remove um jogo pelo identificador.
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        Jogo jogo = entityManager.find(Jogo.class, id);
        if (jogo == null) {
            return ResponseEntity.notFound().build();
        }

        entityManager.remove(jogo);
        return ResponseEntity.noContent().build();
    }
}
