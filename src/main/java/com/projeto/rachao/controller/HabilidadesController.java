package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Habilidades;
import com.projeto.rachao.repository.HabilidadesRepository;
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
@RequestMapping("/api/habilidades")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD das habilidades. */
public class HabilidadesController extends CrudControllerSupport<Habilidades> {

    public HabilidadesController(HabilidadesRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Habilidades> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habilidades> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Habilidades> criar(@RequestBody Habilidades registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habilidades> atualizar(
            @PathVariable Long id,
            @RequestBody Habilidades registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
