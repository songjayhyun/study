package com.example.study.service;

import com.example.study.model.entity.Post;
import com.example.study.model.entity.User;
import com.example.study.respository.PostRepository;
import com.example.study.req.RPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    public Post getPost(final Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post "+id+" not exists"));
    }

    public void createPost(final RPost.CreationReq req) {
        final Post post = Post.builder()
                .title(req.getTitle())
                .content(req.getContent())
                .user(req.getUser())
                .build();

        postRepository.save(post);
    }

    public void updatePost(final RPost.ModificationReq req) {
        final Post post = postRepository.findById(req.getId())
                .orElseThrow(() -> new RuntimeException("post "+req.getId()+" not exists"));

        if(!post.getUser().getId().equals(req.getUser().getId())) {
            throw new RuntimeException("post "+req.getId()+" unauthorized modification");
        }

        if(req.getTitle() != null) {
            post.setTitle(req.getTitle());
        }

        if(req.getContent() != null) {
            post.setContent(req.getContent());
        }

        postRepository.save(post);
    }

    public void deletePost(final Long id, final User signedUser) {
        final Post post =  postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post "+id+" not exists"));

        if(!post.getUser().getId().equals(signedUser.getId())) {
            throw new RuntimeException("post "+id+" unauthorized deletion");
        }

        postRepository.delete(post);
    }
}