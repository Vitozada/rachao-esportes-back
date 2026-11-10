package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Partida;
import com.projeto.rachao.repository.PartidaRepository;
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
@RequestMapping("/api/partidas")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD das partidas. */
public class PartidaController extends CrudControllerSupport<Partida> {

    public PartidaController(PartidaRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Partida> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Partida> criar(@RequestBody Partida registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partida> atualizar(
            @PathVariable Long id,
            @RequestBody Partida registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
