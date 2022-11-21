package com.example.site.controller;

import com.example.site.entity.Post;
import com.example.site.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public ResponseEntity<ArrayList<Post>> getAllPost() {
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable long id) {
        Post persistedPost = postRepository.findById(id);
        return new ResponseEntity<>(persistedPost, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        if(postRepository.findById(id) == null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        postRepository.deleteById(postRepository.findById(id).getPostId());
        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }

    @PostMapping(value = "/posts", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Post> initPost(@RequestBody Post post) throws Exception {
        Post persistedPost = new Post(post.getTitle(), post.getSrc());
        postRepository.save(persistedPost);
        return new ResponseEntity<>(persistedPost, HttpStatus.OK);
    }
}