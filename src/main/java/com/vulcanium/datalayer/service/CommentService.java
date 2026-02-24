package com.vulcanium.datalayer.service;

import com.vulcanium.datalayer.model.Comment;
import com.vulcanium.datalayer.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

    private CommentRepository commentRepository;

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(int id) {
        return commentRepository.findById(id);
    }

    public Comment getCommentWithAssociatedProduct(int id) {
        return commentRepository.findById(id).orElse(null);
    }
}
