package com.example.week9.entity;
//import ...

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name="board")

public class Board {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "board_id", unique = true, nullable = false)
    private Long boardId;

    @Column(length = 15, nullable = false)
    private String title;

    @Column(length = 200, nullable = false)
    private String content;

    @Column(length = 15, nullable = false)
    private String writer;

    //날짜만 표시 시간X
    private LocalDate postDate;

    @PrePersist //jpa의 콜백 메서드. 엔터티가 처음 저장되기 직전에 실행. 즉, 새로운 row 생성시 현재 날짜 저장
    protected void onCreate() { this.postDate = LocalDate.now(); }

    @Column
    private String image;

//    @OneToMany
//    @JoinColumn(name = "comment_id")
//    private Comment comment;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy(value = "createdDate asc") // 댓글 정렬
    @JsonManagedReference
    private List<Comment> comments;
}
