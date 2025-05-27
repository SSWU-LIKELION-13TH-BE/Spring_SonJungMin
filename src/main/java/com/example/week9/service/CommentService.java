package com.example.week9.service;

import com.example.week9.dto.CommentDTO;
import com.example.week9.entity.Board;
import com.example.week9.entity.Comment;
import com.example.week9.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class CommentService {
    private final CommentRepository commentRepository;

    public Optional<Comment> findByCommentId(Long commentId) {
        return commentRepository.findByCommentId(commentId);
    }

    public void postComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void putComment(Board board, CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .commentId(commentDTO.getCommentId())
                .comment(commentDTO.getComment())
                .writer(commentDTO.getWriter())
                .board(board)
                .build();

        commentRepository.save(comment);
    }


    @Transactional
    public void deleteComment(Long commentId){
        commentRepository.deleteByCommentId(commentId);
    }

}

