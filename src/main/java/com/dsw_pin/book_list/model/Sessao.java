package com.dsw_pin.book_list.model;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Sessao {

    @Id
    private String id;

    @Column(nullable = false)
    private Date dataCriacao;

    @Column(nullable = false)
    private boolean valida;

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public boolean isValida() {
        return valida;
    }

    public void setValida(boolean valida) {
        this.valida = valida;
    }
}
