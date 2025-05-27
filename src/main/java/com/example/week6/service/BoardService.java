package com.example.week6.service;

import com.example.week6.dto.BoardDTO;
import com.example.week6.entity.Board;
import com.example.week6.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class BoardService {
    private final BoardRepository boardRepository;
    private final S3Service s3Service;

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


//    @Transactional
//    public void deleteBoard(Long boardId) {
//        boardRepository.deleteByBoardId(boardId);
//    }

//    @Transactional
//    public void deleteUpload(Long boardId) {
//        Board board = boardRepository.findById(boardId)
//                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
//
//        // S3 이미지 삭제
//        if (board.getImageFileName() != null) {
//            s3Service.deleteFile(board.getImageFileName());
//        }
//
//        // 게시글 삭제
//        boardRepository.delete(board);
//    }


    @Transactional
    //이미지 포함 게시글 생성
    public void ImageBoard(BoardDTO request) throws IOException {

        String savedImageURI = s3Service.upload(request.getImage());

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .image(savedImageURI)
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public String getFileUrl(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시물입니다."));

        String fileName = board.getImage();
        return s3Service.getFileUrl(fileName);
    }

    @Transactional
    // 일반 게시물 삭제와 사진 게시물 삭제를 하나로 합쳤다
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시물입니다."));

        if(board.getImage() != null && !board.getImage().isEmpty()) {
            s3Service.deleteFile(board.getImage());
        }

        boardRepository.deleteByBoardId(boardId);
    }
}

