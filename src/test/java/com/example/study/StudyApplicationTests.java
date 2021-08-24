package com.example.study;

import com.example.study.respository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyApplicationTests {

	@Autowired
	PostRepository postRepository;

	@Test
	void contextLoads() {
		postRepository.findById(1L).ifPresent(post -> {
			System.out.println("post : "+post.getId()+"/"+post.getTitle()+"/"+post.getContent());
		});
	}
}
