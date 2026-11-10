package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.JogadorTime;
import com.projeto.rachao.repository.JogadorTimeRepository;
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
@RequestMapping("/api/jogadores-times")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD da associação entre jogadores e times. */
public class JogadorTimeController extends CrudControllerSupport<JogadorTime> {

    public JogadorTimeController(JogadorTimeRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<JogadorTime> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JogadorTime> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<JogadorTime> criar(@RequestBody JogadorTime registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JogadorTime> atualizar(
            @PathVariable Long id,
            @RequestBody JogadorTime registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
