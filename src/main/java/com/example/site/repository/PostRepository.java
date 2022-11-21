package com.example.site.repository;

import com.example.site.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByTitle(long id);

    ArrayList<Post> findAll();

}
