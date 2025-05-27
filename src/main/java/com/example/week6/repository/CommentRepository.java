package com.example.week6.repository;

import com.example.week6.entity.Board;
import com.example.week6.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Optional<Comment> findByCommentId(Long commentId);

    void deleteByCommentId(Long commentId);
}
