package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Mensagem;
import com.projeto.rachao.repository.MensagemRepository;
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
@RequestMapping("/api/mensagens")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD das mensagens dos chats. */
public class MensagemController extends CrudControllerSupport<Mensagem> {

    public MensagemController(MensagemRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Mensagem> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Mensagem> criar(@RequestBody Mensagem registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mensagem> atualizar(
            @PathVariable Long id,
            @RequestBody Mensagem registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
