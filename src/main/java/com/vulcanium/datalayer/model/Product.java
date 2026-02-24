package com.vulcanium.datalayer.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Comment> comments = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    private final Set<Category> categories = new HashSet<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.changeProduct(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.changeProduct(null);
    }
}
