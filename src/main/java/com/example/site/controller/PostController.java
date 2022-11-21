package com.example.site.controller;

import com.example.site.entity.Post;
import com.example.site.repository.PostRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostRepository postRepository;




    @GetMapping("/post")
    public ResponseEntity<ArrayList<Post>> getAllPost() {
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Post> getPost(@PathVariable long id) {
        Post persistedPost = postRepository.findById(id);
        return new ResponseEntity<>(persistedPost, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        if(postRepository.findById(id) == null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Post post = postRepository.findById(id);
        postRepository.deleteById(post.getPostId());
        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }

    @PostMapping(value = "/initPost", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Post> initPost(@RequestBody Post post) throws Exception {
        Post persistedPost = new Post(post.getTitle(), post.getSrc());
        postRepository.save(persistedPost);

        return new ResponseEntity<>(persistedPost, HttpStatus.OK);
    }
}