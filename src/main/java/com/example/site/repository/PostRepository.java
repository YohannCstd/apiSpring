package com.example.site.repository;

import com.example.site.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Post findByTitle(String title);

    Post findById(long id);


    ArrayList<Post> findAll();

}
