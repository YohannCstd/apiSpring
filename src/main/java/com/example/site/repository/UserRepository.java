package com.example.site.repository;

import com.example.site.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    ArrayList<User> findAll();
    Boolean existsByName(String name);
    User findByName(String name);

}
