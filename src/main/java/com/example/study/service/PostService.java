package com.example.study.service;

import com.example.study.httpException.ResponseError;
import com.example.study.model.entity.Post;
import com.example.study.model.entity.PostImage;
import com.example.study.model.entity.User;
import com.example.study.respository.PostImageRepository;
import com.example.study.respository.PostRepository;
import com.example.study.req.RPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    @Value("${upload.rootPath}")
    private String uploadRootPath;

    public List<Post> getPostList() {
        return postRepository.findAll();
    }

    public Post getPost(final Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> ResponseError.NotFound.POST_NOT_EXISTS.getResponseException(id.toString()));
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

    public void savePostImage(final Long id, final User signedUser, final MultipartFile file) {
        final Post post =  postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("post "+id+" not exists"));

        if(!post.getUser().getId().equals(signedUser.getId())) {
            throw new RuntimeException("post "+id+" unauthorized deletion");
        }

        final ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        final String date = String.format("%04d%02d%02d", zonedDateTime.getYear(), zonedDateTime.getMonthValue(), zonedDateTime.getDayOfMonth());

        final Tika tika = new Tika();
        String mimeTypeString = null;
        try {
            mimeTypeString = tika.detect(file.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException("invalid file type");
        }

        if(!Set.of(
                MediaType.IMAGE_PNG_VALUE,
                MediaType.IMAGE_JPEG_VALUE).contains(mimeTypeString)) {
            throw new RuntimeException("invalid file type");
        }

        final String extension = mimeTypeString.substring(mimeTypeString.lastIndexOf('/')+1);

        final String fileName = UUID.randomUUID().toString() + "-" + zonedDateTime.getNano() + "." + extension;

        final String folderPath = "/" + date;

        final File folder = new File(uploadRootPath + folderPath);

        if(!folder.exists()) {
            folder.mkdir();
        }

        final String path = "/" + date + "/" + fileName;

        try {
            file.transferTo(new File(uploadRootPath + path));
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }

        postImageRepository.save(PostImage.builder()
                .post(post)
                .path(path)
                .build());
    }
}