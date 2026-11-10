package com.projeto.rachao.controller;

import com.projeto.rachao.entidades.Chat;
import com.projeto.rachao.repository.ChatRepository;
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
@RequestMapping("/api/chats")
@CrossOrigin(origins = "http://localhost:4200")
/** Endpoints CRUD dos chats associados às partidas. */
public class ChatController extends CrudControllerSupport<Chat> {

    public ChatController(ChatRepository repository) {
        super(repository);
    }

    @GetMapping
    public List<Chat> listar() {
        return listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> buscar(@PathVariable Long id) {
        return buscarRegistro(id);
    }

    @PostMapping
    public ResponseEntity<Chat> criar(@RequestBody Chat registro) {
        return criarRegistro(registro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chat> atualizar(
            @PathVariable Long id,
            @RequestBody Chat registro
    ) {
        return atualizarRegistro(id, registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        return apagarRegistro(id);
    }
}
