package com.example.week6.service;

import com.example.week6.dto.BoardDTO;
import com.example.week6.dto.CommentDTO;
import com.example.week6.entity.Board;
import com.example.week6.entity.Comment;
import com.example.week6.repository.BoardRepository;
import com.example.week6.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;

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

