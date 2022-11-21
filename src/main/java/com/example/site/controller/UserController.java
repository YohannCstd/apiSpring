package com.example.site.controller;

import com.example.site.entity.User;
import com.example.site.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //Get all users with admin permissions
    /*@GetMapping("/admin_users")
    public ResponseEntity<ArrayList<User>> getAllUsersWithAdminPermissions(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }*/
}
