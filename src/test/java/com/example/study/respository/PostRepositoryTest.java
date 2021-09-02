package com.example.study.respository;

import com.example.study.model.entity.Post;
import com.example.study.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void testFindById() {
        final Optional<Post> postOptional = postRepository.findById(1L);
        postOptional.ifPresent(post -> {
            System.out.println("post : "+post.getId()+"/"+post.getTitle()+"/"+post.getContent());
        });
    }

    @Test
    public void testFindByTitle() {
        final Optional<Post> postOptional = postRepository.findByTitle("테스트제목 2");
        postOptional.ifPresent(post -> {
            System.out.println("post : "+post.getId()+"/"+post.getTitle()+"/"+post.getContent());
        });
    }

    @Test
    public void testInsert() {
        final Post post = Post.builder()
                .title("test title")
                .content("test content")
                .user(User.builder().id(1L).build())
                .build();

        postRepository.save(post);

        Assertions.assertNotNull(post.getId());

        System.out.println("post : "+post.getId()+"/"+post.getTitle()+"/"+post.getContent());
    }

    @Test
    public void testUpdate() {
        final Optional<Post> postOptional = postRepository.findById(1L);

        Assertions.assertTrue(postOptional.isPresent());

        final Post post = postOptional.get();
        post.setContent(post.getContent() + " update");

        postRepository.save(post);

        System.out.println("post : "+post.getId()+"/"+post.getTitle()+"/"+post.getContent());
    }

    @Test
    void testDelete() {

        final long originCount = postRepository.count();

        Optional<Post> postOptional = postRepository.findAll()
                .stream().max(Comparator.comparing(Post::getId));

        Assertions.assertTrue(postOptional.isPresent());

        final Post post = postOptional.get();
        postRepository.delete(post);

        final long count = postRepository.count();

        assertThat(originCount).isEqualTo(count+1);
    }
    

    @Test
    void 스프링데이터확인() {
        List<Post> post = postRepository.findByTitleLikeAndIdGreaterThanOrderByIdDesc("%타이틀%", 1L);
        System.out.println("post = " + post);
    }
    
//    @Test
//    void testQueryUpdate() {
//        int updateCount = postRepository.updateTitle(4L, "타이틀 테스트");
//        System.out.println("updateCount = " + updateCount);
//    }

    @Transactional
    @Test
    public void testFindById2() {
        final Optional<Post> postOptional = postRepository.findById(1L);
        postOptional.ifPresent(post -> {
            System.out.println("post : "
                    +post.getId()+"/"
                    +post.getTitle()+"/"
                    +post.getContent()+"/"
                    +post.getUser().getId()+"/"
                    +post.getUser().getName());
        });
    }
}