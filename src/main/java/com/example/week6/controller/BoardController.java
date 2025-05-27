package com.example.week6.controller;

import com.example.week6.dto.BoardDTO;
import com.example.week6.entity.Board;
import com.example.week6.repository.BoardRepository;
import com.example.week6.service.BoardService;
import com.example.week6.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;
    private final S3Service s3Service;

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

    @PostMapping("/updateBoard")
    public void updateBoard(@RequestBody BoardDTO boardDTO){
        boardService.putBoard(boardDTO);
    }

    @DeleteMapping("/deleteBoard{boardId}")
    public void deleteBoard(@PathVariable(name="boardId") Long boardID) {
        boardService.deleteBoard(boardID);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@ModelAttribute BoardDTO imageBoardDTO){
        try {
            BoardDTO request = BoardDTO.builder()
                    .title(imageBoardDTO.getTitle())
                    .content(imageBoardDTO.getContent())
                    .image(imageBoardDTO.getImage())
                    .writer(imageBoardDTO.getWriter())
                    .build();

            boardService.ImageBoard(request);

            return ResponseEntity.ok("파일 업로드 성공");
        } catch (Exception e) {
            log.error("파일 업로드 실패", e);
            return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/{boardId}/image")
    public ResponseEntity<String> getFileUrl(@PathVariable Long boardId) {
        try {
            String imageUrl = boardService.getFileUrl(boardId);
            return ResponseEntity.ok(imageUrl);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam String url) {
        try {
            s3Service.deleteFile(url);  // 네가 작성한 메서드 호출
            return ResponseEntity.ok("삭제 성공");
        } catch (Exception e) {
            log.error("파일 삭제 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
        }
    }


}
