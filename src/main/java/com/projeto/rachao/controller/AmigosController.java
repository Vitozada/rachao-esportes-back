package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Amigos;
import com.projeto.rachao.repository.AmigosRepository;
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
@RequestMapping("/api/amigos")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD de amizades entre jogadores. */
public class AmigosController extends CrudControllerSupport<Amigos> {

    public AmigosController(AmigosRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Amigos> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Amigos> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Amigos> criar(@RequestBody Amigos registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Amigos> atualizar(
            @PathVariable Long id,
            @RequestBody Amigos registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
