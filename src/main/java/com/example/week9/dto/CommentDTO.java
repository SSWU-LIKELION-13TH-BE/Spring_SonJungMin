package com.example.week9.dto;

import com.example.week9.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter

public class CommentDTO {
    private Long commentId;
    private String comment;
    private String createdDate;
    private String writer;
    private Board board;
}