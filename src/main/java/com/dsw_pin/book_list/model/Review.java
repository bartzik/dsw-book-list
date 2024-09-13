package com.dsw_pin.book_list.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB_REVIEW")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String comment;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
