package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Desempenho;
import com.projeto.rachao.repository.DesempenhoRepository;
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
@RequestMapping("/api/desempenhos")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD do desempenho dos jogadores. */
public class DesempenhoController extends CrudControllerSupport<Desempenho> {

    public DesempenhoController(DesempenhoRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Desempenho> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desempenho> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Desempenho> criar(@RequestBody Desempenho registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Desempenho> atualizar(
            @PathVariable Long id,
            @RequestBody Desempenho registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
