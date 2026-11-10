package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.FormaPagamento;
import com.projeto.rachao.repository.FormaPagamentoRepository;
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
@RequestMapping("/api/formas-pagamento")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD das formas de pagamento. */
public class FormaPagamentoController extends CrudControllerSupport<FormaPagamento> {

    public FormaPagamentoController(FormaPagamentoRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<FormaPagamento> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamento> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<FormaPagamento> criar(@RequestBody FormaPagamento registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamento> atualizar(
            @PathVariable Long id,
            @RequestBody FormaPagamento registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
