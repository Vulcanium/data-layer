package com.vulcanium.datalayer.repository;

import com.vulcanium.datalayer.model.Product;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Override
    @NullMarked
    @EntityGraph(attributePaths = {"comments", "categories"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"comments", "categories"})
    Optional<Product> findById(int id);

    @EntityGraph(attributePaths = {"comments", "categories"})
    List<Product> findByName(String name);

    @EntityGraph(attributePaths = {"comments", "categories"})
    List<Product> findByCategoriesName(String categoryName);

    @EntityGraph(attributePaths = {"comments", "categories"})
    List<Product> findByCostLessThan(int cost);
}
