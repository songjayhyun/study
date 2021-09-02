package com.example.study.respository;

import com.example.study.model.entity.Post;
import com.example.study.model.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findByPostOrderByIdAsc(Post post);


}