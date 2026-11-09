package com.projeto.rachao.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "ESPORTES")
public class Esportes {

    @Enumerated(EnumType.STRING)
    @Column(name = "ESPORTE")
    private Esportes esporte;

    public static enum EsportesLista {
        BASQUETE,
        VOLEI,
        FUTEBOL
    }
}