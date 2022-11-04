package com.example.site.repository;

import com.example.site.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findById(long customerId);
    Iterable<User> findAll();
}
