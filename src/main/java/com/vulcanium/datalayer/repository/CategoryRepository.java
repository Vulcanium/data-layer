package com.vulcanium.datalayer.repository;

import com.vulcanium.datalayer.model.Category;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Override
    @NullMarked
    @EntityGraph(attributePaths = {"products"})
    List<Category> findAll();

    @EntityGraph(attributePaths = {"products"})
    Optional<Category> findById(int id);
}
