package com.example.study.controller;


import com.example.study.model.entity.Post;
import com.example.study.respository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class PostController {
    private final PostRepository postRepository;

    // [GET] /v1/posts
    // Post 리스트 조회
    // @RequestMapping(method = RequestMethod.GET, value = "/posts")
    @GetMapping("/posts")
    public List<Post> getPostList(
            @RequestParam("query") String query
    ) {
        log.info("query : " + query);
        return postRepository.findAll();
    }

    // [GET] /v1/posts/{id}
    // Post 1개 상세 조회
    @GetMapping("/posts/{id}")
    public Post getPost(
            @PathVariable Long id
    ) {
        log.info("id : " + id);
        return postRepository.getById(id);
    }

    // [POST] /v1/posts
    // Post 생성
//    @PostMapping("/posts")
//    public ResponseEntity<Void> createPost(
//            // TODO @RequestBody ReqPost reqPost
//    ) {
//        final User user = User.builder()
//                .id(1L)
//                .build();
//
//        final Post post = Post.builder()
//                .title("title")
//                .content("content")
//                .user(user)
//                .build();
//
//        postRepository.save(post);
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    // [PUT] /v1/posts
    // Post 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Long id
            // TODO @RequestBody ReqPost reqPost
    ) {
        final Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not exists " + id));

        post.setContent(post.getContent() + " update");

        postRepository.save(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // [DELETE] /v1/posts
    // Post 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id
    ) {
        final Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not exists " + id));

        postRepository.delete(post);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}