package com.example.week6.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class BoardDTO {
    private Long boardId;
    private String title;
    private String content;
    private String writer;
    private Date postDate;
}
