package com.example.week6.repository;

import com.example.week6.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Integer> {
    //게시글 가져오기 read (get)
    //Optional은 값이 있을 수도 있고, 없을 수도 있는 상황을 처리하는 컨테이너 객체
    Optional<Board> findByBoardId(Long boardId);

    //게시글 작성하기 create (post)
    //void.save(); //이건 명명 안 하고 바로 적어도 됨. 자동지원됨

    //게시글 삭제하기 delete (delete)
    void deleteByBoardId(Long boardId);

    List<Board> boardId(Long boardId);
}
