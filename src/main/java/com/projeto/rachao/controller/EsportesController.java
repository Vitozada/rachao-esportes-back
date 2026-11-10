package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Esportes;
import com.projeto.rachao.repository.EsportesRepository;
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
@RequestMapping("/api/esportes")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD dos esportes disponíveis. */
public class EsportesController extends CrudControllerSupport<Esportes> {

    public EsportesController(EsportesRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Esportes> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Esportes> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Esportes> criar(@RequestBody Esportes registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Esportes> atualizar(
            @PathVariable Long id,
            @RequestBody Esportes registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
