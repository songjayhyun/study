package com.example.study.respository;

import com.example.study.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /*
    SELECT *
    FROM post
    WHERE title = :title
     */
    Optional<Post> findByTitle(String title);

    /*
    SELECT *
    FROM post
    WHERE title like '%테스트%'
    AND id > 1
    ORDER BY id DESC
     */
    List<Post> findByTitleLikeAndIdGreaterThanOrderByIdDesc(String title, Long id);

//    @Modifying
//    @Query("UPDATE post SET title = :title WHERE id >= :midId")
//    int updateTitle(
//            @Param("midId") Long midId,
//            @Param("title") String title
//    );

}
