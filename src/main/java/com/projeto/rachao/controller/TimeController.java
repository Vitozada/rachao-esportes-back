package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Time;
import com.projeto.rachao.repository.TimesRepository;
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
@RequestMapping("/api/times")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD dos times. */
public class TimeController extends CrudControllerSupport<Time> {

    public TimeController(TimesRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Time> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Time> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Time> criar(@RequestBody Time registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Time> atualizar(
            @PathVariable Long id,
            @RequestBody Time registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
