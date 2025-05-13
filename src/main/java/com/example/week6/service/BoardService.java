package com.example.week6.service;

import com.example.week6.dto.BoardDTO;
import com.example.week6.entity.Board;
import com.example.week6.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class BoardService {
    private final BoardRepository boardRepository;

    public Optional<Board> findByBoardId(Long boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    public void postBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public void putBoard(BoardDTO boardDTO) {
        Board board = Board.builder()
                .boardId(boardDTO.getBoardId())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getContent())
                .postDate(LocalDate.now())
                .build();

        boardRepository.save(board);
    }


    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.deleteByBoardId(boardId);
    }

}

