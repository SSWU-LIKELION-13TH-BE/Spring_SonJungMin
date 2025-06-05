package com.example.week9.service;

import com.example.week9.dto.BoardDTO;
import com.example.week9.entity.Board;
import com.example.week9.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
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

    @Transactional
    //이미지 포함 게시글 생성
    public void ImageBoard(BoardDTO request) throws IOException {

        String savedImageURI = s3Service.upload(request.getImage());

        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .image(List.of(savedImageURI))
                .build();

        boardRepository.save(board);
    }

    @Transactional
    public String getFileUrl(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시물입니다."));

        //원래 이미지파일이 String일 때 사용하던 코드
//        String fileName = board.getImage();
//        return s3Service.getFileUrl(fileName);

        //여긴 JSON 형식으로 바꾼 후 사용하는 코드
        List<String> images = board.getImage();
        if (images != null && !images.isEmpty()) {
            return s3Service.getFileUrl(images.get(0)); // 첫 번째 이미지 반환
        } else {
            return null; // 또는 기본 URL 반환
        }
    }

    @Transactional
    // 일반 게시물 삭제와 사진 게시물 삭제를 하나로 합쳤다
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시물입니다."));

        //원래 이미지파일이 String일 때 사용하던 코드
//        if(board.getImage() != null && !board.getImage().isEmpty()) {
//            s3Service.deleteFile(board.getImage());
//        }

        //여긴 JSON 형식으로 바꾼 후 사용하는 코드
        if (board.getImage() != null && !board.getImage().isEmpty()) {
            for (String fileName : board.getImage()) {
                s3Service.deleteFile(fileName); // ✅ 여러 이미지 삭제
            }
        }

        boardRepository.deleteByBoardId(boardId);
    }
}

