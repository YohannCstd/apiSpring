package com.example.site.controller;

import com.example.site.entity.Post;
import com.example.site.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

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
    @PostMapping(value = "/posts")
    public ResponseEntity<String> initPost(@RequestPart MultipartFile document, String title, String note) throws Exception {
        //Check if document is img
        if(!Objects.equals(document.getContentType(), "image/jpeg")) return new ResponseEntity<>("Only JPEG !",HttpStatus.BAD_REQUEST);
        //Create path
        Path currentRelativePath = Paths.get("");
        String currentPath = currentRelativePath.toAbsolutePath().toString();
        String realPathToUploads = currentPath + "\\src\\main\\resources\\uploads\\";
        //Check if folder upload is already exist
        if(! new File(realPathToUploads).exists())
        {
            new File(realPathToUploads).mkdir();
        }
        //New name
        File folder = new File(realPathToUploads);
        File[] files = folder.listFiles();
        int count = files.length;
        String orgName = count+1+".jpg";
        //Destination
        String filePath = realPathToUploads + orgName;
        File dest = new File(filePath);
        //Check if title null
        if(title == null) return new ResponseEntity<>("Title is null",HttpStatus.BAD_REQUEST);
        //Save Post
        postRepository.save(new Post(title,note,"localhost:9080/src/main/resources/uploads/"+orgName));
        //Download img
        document.transferTo(dest);
        return new ResponseEntity<>("Save with success !", HttpStatus.OK);
    }
    @PutMapping(value = "/posts/{id}")
    public ResponseEntity<String> updatePost(@PathVariable long id, @RequestPart String title, String note) {
        Post post = postRepository.findById(id);
        post.setTitle(title);
        post.setNote(note);
        postRepository.save(post);
        return new ResponseEntity<>("Modified", HttpStatus.OK);
    }

}