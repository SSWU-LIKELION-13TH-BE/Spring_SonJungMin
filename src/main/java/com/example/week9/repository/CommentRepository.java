package com.example.week9.repository;

import com.example.week9.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Optional<Comment> findByCommentId(Long commentId);

    void deleteByCommentId(Long commentId);
}
