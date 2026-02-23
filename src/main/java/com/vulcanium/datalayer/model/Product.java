package com.vulcanium.datalayer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Setter
    private String name;

    @Setter
    private String description;

    @Setter
    private int cost;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private final List<Comment> comments;

    public Product() {
        this.comments = new ArrayList<>();
    }
}
