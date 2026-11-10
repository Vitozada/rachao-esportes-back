package com.projeto.rachao.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Reúne operações CRUD comuns para evitar repetição nos controllers.
 *
 * @param <T> tipo da entidade controlada
 */
public abstract class CrudControllerSupport<T> {

    protected final JpaRepository<T, Long> repository;

    protected CrudControllerSupport(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    protected List<T> listarRegistros() {
        return repository.findAll();
    }

    protected ResponseEntity<T> buscarRegistro(Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    protected ResponseEntity<T> criarRegistro(T registro) {
        return ResponseEntity.status(201).body(repository.save(registro));
    }

    protected ResponseEntity<T> atualizarRegistro(Long id, T registro) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        definirId(registro, id);
        return ResponseEntity.ok(repository.save(registro));
    }

    protected ResponseEntity<Void> apagarRegistro(Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
