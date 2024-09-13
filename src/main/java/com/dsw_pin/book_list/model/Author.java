package com.dsw_pin.book_list.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB_AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
