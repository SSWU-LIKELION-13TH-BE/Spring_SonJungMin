package com.example.week6.controller;

import com.example.week6.dto.BoardDTO;
import com.example.week6.entity.Board;
import com.example.week6.repository.BoardRepository;
import com.example.week6.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;

    @GetMapping("/getBoard")
    public Optional<Board> getBoard(@RequestParam(name = "boardId") Long boardId) {
        return boardService.findByBoardId(boardId);
    }

    @PostMapping("/postBoard")
    public void postBoard(@RequestBody BoardDTO boardDTO) {
        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();
        boardService.postBoard(board);
    }

    @DeleteMapping("/deleteBoard{boardId}")
    public void deleteBoard(@PathVariable(name="boardId") Long boardID) {
        boardService.deleteBoard(boardID);
    }
}
