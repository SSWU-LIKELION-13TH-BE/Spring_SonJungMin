package com.example.week6.entity;
//import ...

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "comments")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    private Long commentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;


    private String createdDate;

    @PrePersist //jpa의 콜백 메서드. 엔터티가 처음 저장되기 직전에 실행. 즉, 새로운 row 생성시 현재 날짜 저장
    protected void onCreate() {
        this.createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

//    @Column(name = "modified_date")
//    @LastModifiedDate
//    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private Board board;

//    후에 로그인 기능 구현하면 유저와 합치면 됨(N:1)로
//    @ManyToOne
//    @JoinColumn(name = "user_id")
    @Column(name = "comment_writer")
    private String writer; // 작성자

    //댓글 수정
    public void update(String comment) {
        this.comment = comment;
    }
}