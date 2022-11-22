package com.example.site.repository;

import com.example.site.entity.User;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User,Long> {
    ArrayList<User> findAll();
    Boolean existsByName(String name);
    User findByName(String name);

}
