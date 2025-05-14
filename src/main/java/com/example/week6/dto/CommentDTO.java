package com.example.week6.dto;

import com.example.week6.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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