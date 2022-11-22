package com.example.site.controller;

import com.example.site.entity.Post;
import com.example.site.repository.PostRepository;
import com.example.site.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;

//Ajoute au début de la route "/api" pour ne plus avoir à mettre /api à chaque fois
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostServices postServices;
    @Autowired
    private PostRepository postRepository;

    //GET
    //La route api/posts avec la méthode GET renvoie tout le contenu de la table Post avec la fonction findAll()
    @GetMapping("/posts")
    public ResponseEntity<?> getAllPost() {
        if(postRepository.findAll() == null) return new ResponseEntity<>("Post not found !",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    //GET
    //Renvoie le Post correspondant à l'id passée en paramètre de l'URL
    //Le {id} se charge de recupérer le contenu présent après le /posts/ pour le passer en paramètre de le fonction
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable long id) {
        if(postRepository.findById(id) == null) return new ResponseEntity<>("Post not found !",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postRepository.findById(id), HttpStatus.OK);
    }

    //DELETE
    //Supprime le Post ayant comme id celui passé en paramètre de l'URL
    //Renvoie Deleted si réussite, NOT FOUND si l'id n'est pas reconnu
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        if(postRepository.findById(id) == null)return new ResponseEntity<>("Post not found !",HttpStatus.NOT_FOUND);
        postRepository.deleteById(postRepository.findById(id).getPostId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    //POST
    //Stocke l'image.jpeg sur le serveur puis créer et sauvegarde un nouveau Post en utilisant les données envoyé depuis le body de la requête.
    //Renvoie BAD REQUEST si le titre est null
    //Renvoie OK Save with success ! si succès
    @PostMapping(value = "/posts")
    public ResponseEntity initPost(@RequestPart(value = "document", required = false)MultipartFile document, String title, String note) throws Exception {
        return ResponseEntity.ok(postServices.createPost(document,title,note));
    }

    //PUT
    //Modifie le POST (seulement titre et note) ayant comme id l'id passé en paramètre de l'URL
    //Renvoie Modified, OK si succès
    @PutMapping(value = "/posts/{id}")
    public ResponseEntity updatePost(@PathVariable long id, @RequestPart (value = "title", required = false) String title, String note) {
        return ResponseEntity.ok(postServices.update(id,title,note));
    }

}