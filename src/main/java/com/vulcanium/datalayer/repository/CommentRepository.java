package com.vulcanium.datalayer.repository;

import com.vulcanium.datalayer.model.Comment;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Override
    @NullMarked
    @EntityGraph(attributePaths = {"product"})
    List<Comment> findAll();

    @EntityGraph(attributePaths = {"product"})
    Optional<Comment> findById(int id);
}
