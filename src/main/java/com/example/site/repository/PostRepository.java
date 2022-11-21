package com.example.site.repository;

import com.example.site.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByTitle(String title);

    Optional<Post> findById(Long id);


    ArrayList<Post> findAll();

}
