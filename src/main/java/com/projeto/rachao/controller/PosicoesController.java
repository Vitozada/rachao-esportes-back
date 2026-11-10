package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Posicoes;
import com.projeto.rachao.repository.PosicoesRepository;
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
@RequestMapping("/api/posicoes")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD das posições esportivas. */
public class PosicoesController extends CrudControllerSupport<Posicoes> {

    public PosicoesController(PosicoesRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Posicoes> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Posicoes> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Posicoes> criar(@RequestBody Posicoes registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Posicoes> atualizar(
            @PathVariable Long id,
            @RequestBody Posicoes registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
