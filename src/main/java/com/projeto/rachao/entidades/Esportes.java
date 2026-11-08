package com.projeto.rachao.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ESPORTES")
public class Esportes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean volei;
    
    private Boolean basquete;
    
    private Boolean futebol;

    public Esportes() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getVolei() { return volei; }
    public void setVolei(Boolean volei) { this.volei = volei; }
    public Boolean getBasquete() { return basquete; }
    public void setBasquete(Boolean basquete) { this.basquete = basquete; }
    public Boolean getFutebol() { return futebol; }
    public void setFutebol(Boolean futebol) { this.futebol = futebol; }
}