package com.example.week6.controller;

import com.example.week6.dto.BoardDTO;
import com.example.week6.dto.CommentDTO;
import com.example.week6.entity.Board;
import com.example.week6.entity.Comment;
import com.example.week6.repository.BoardRepository;
import com.example.week6.repository.CommentRepository;
import com.example.week6.service.BoardService;
import com.example.week6.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.util.Optional;


@RestController
@RequestMapping("api/comment")
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;
    private final BoardRepository boardRepository;

    @GetMapping("/getComment")
    public Optional<Comment> getComent(@RequestParam(name = "commentId") Long commentId) {
        return commentService.findByCommentId(commentId);
    }

    @PostMapping("/board/{board_id}/postComment")
    public void postComment(@PathVariable("board_id") Long boardId, @RequestBody CommentDTO commentDTO) {

        Optional<Board> board = boardRepository.findByBoardId(boardId);

        if (board.isPresent()) {
            Comment comment = Comment.builder()
                    .comment(commentDTO.getComment())
                    .writer(commentDTO.getWriter())
                    .board(board.get())
                    .build();
            commentService.postComment(comment);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.");
        }

    }

    @PostMapping("/board/{board_id}/updateComment")
    public void updateComment(@PathVariable("board_id") Long boardId, @RequestBody CommentDTO commentDTO) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);

        if (board.isPresent()) {
            commentService.putComment(board.get(), commentDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다.");
        }

    }


    @DeleteMapping("/deleteComment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
    }
}

