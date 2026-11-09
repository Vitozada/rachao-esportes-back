package com.projeto.rachao.entidades;

import jakarta.persistence.*;

public class StatusPartida {

    public StatusPartida() {
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private StatusPartida status;

    public enum StatusAtual {
        PENDENTE,
        EM_ANDAMENTO,
        CONCLUIDA
    }
}
