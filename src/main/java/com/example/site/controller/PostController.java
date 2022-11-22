package com.example.site.controller;

import com.example.site.repository.PostRepository;
import com.example.site.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller qui permet de gérer les postes
 * */
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostServices postServices;
    @Autowired
    private PostRepository postRepository;

    /**
     * Méthode qui permet d'afficher tout les postes qui sont dans la base de données
     * @return une liste de postes
     * */
    @GetMapping("/posts")
    public ResponseEntity<?> getAllPost() {
        if(postRepository.findAll() == null) return new ResponseEntity<>("Post not found !",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Méthode qui permet d'afficher un poste correspondant à l'id passée en paramètre
     * @param id
     * @return un poste correspondant à l'id
     * */
    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable long id) {
        if(postRepository.findById(id) == null) return new ResponseEntity<>("Post not found !",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(postRepository.findById(id), HttpStatus.OK);
    }

    /**
     * Méthode qui permet de supprimer un poste avec l'id passé en paramètre de l'url
     * @param id
     * @return String avec le message adapté
     * */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        if(postRepository.findById(id) == null)return new ResponseEntity<>("Post not found !",HttpStatus.NOT_FOUND);
        postRepository.deleteById(postRepository.findById(id).getPostId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    /**
     * Méthode qui permet d'inserer un nouveau poste dans la base de donnée
     * @param document
     * @param note
     * @param title
     * @return HttpStatus code ainsi qu'un message adapté
     * */
    @PostMapping(value = "/posts")
    public ResponseEntity initPost(@RequestPart(value = "document", required = false)MultipartFile document, String title, String note) throws Exception {
        return ResponseEntity.ok(postServices.createPost(document,title,note));
    }

    /**
     * Méthode qui permet de modifier (titre et/ou note) d'un poste correspondant à l'id passé en paramètre de l'url
     * @param id
     * @param title
     * @param note
     * @return un message adapté en fonction de si la suppresion à fonctionnée
     * */
    @PutMapping(value = "/posts/{id}")
    public ResponseEntity updatePost(@PathVariable long id, @RequestPart (value = "title", required = false) String title, String note) {
        return ResponseEntity.ok(postServices.update(id,title,note));
    }

}