package com.example.site.services;

import com.example.site.entity.Post;
import com.example.site.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class PostServices {

    @Autowired
    private PostRepository postRepository;

    public ResponseEntity<String> createPost(MultipartFile document, String title, String note) throws IOException {
        //Vérifie une image est envoyée
        if(document == null) return new ResponseEntity<>("Must send an image !", HttpStatus.BAD_REQUEST);
        //Check if document is img
        if(!Objects.equals(document.getContentType(), "image/jpeg")) return new ResponseEntity<>("Only JPEG !",HttpStatus.BAD_REQUEST);
        if(title == null) return new ResponseEntity<>("Title is null",HttpStatus.BAD_REQUEST);
        if(title.length() > 50) return new ResponseEntity<>("Title must be shorter than 50chr !", HttpStatus.BAD_REQUEST);
        if(note != null) if(note.length() > 255) return new ResponseEntity<>("Note must be shorter than 255chr !", HttpStatus.BAD_REQUEST);
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
        //Save Post
        postRepository.save(new Post(title,note,"localhost:9080/src/main/resources/uploads/"+orgName));
        //Download img
        document.transferTo(dest);
        return new ResponseEntity<>("Save with success !", HttpStatus.OK);
    }

    public ResponseEntity<String> update(long id,String title,String note){
        if(title == null) return new ResponseEntity<>("Title must be sent !", HttpStatus.BAD_REQUEST);
        if(title.length() > 50) return new ResponseEntity<>("Title must be shorter than 50chr !", HttpStatus.BAD_REQUEST);
        if(note.length() > 255) return new ResponseEntity<>("Note must be shorter than 255chr !", HttpStatus.BAD_REQUEST);
        Post post = postRepository.findById(id);
        post.setTitle(title);
        post.setNote(note);
        postRepository.save(post);
        return new ResponseEntity<>("Modified", HttpStatus.OK);
    }
}
