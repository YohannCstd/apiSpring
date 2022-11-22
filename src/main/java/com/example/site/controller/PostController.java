package com.example.site.controller;

import com.example.site.entity.Post;
import com.example.site.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

@RestController

//Ajoute au début de la route "/api" pour ne plus avoir à mettre /api à chaque fois
@RequestMapping("/api")
public class PostController {

    //PostRepository permet de faire le lien avec la table Post
    @Autowired
    private PostRepository postRepository;

    //GET
    //La route api/posts avec la méthode GET renvoie tout le contenu de la table Post avec la fonction findAll()
    @GetMapping("/posts")
    public ResponseEntity<ArrayList<Post>> getAllPost() {
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    //GET
    //Renvoie le Post correspondant à l'id passée en paramètre de l'URL
    //Le {id} se charge de recupérer le contenu présent après le /posts/ pour le passer en paramètre de le fonction
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPost(@PathVariable long id) {
        Post persistedPost = postRepository.findById(id);
        return new ResponseEntity<>(persistedPost, HttpStatus.OK);
    }

    //DELETE
    //Supprime le Post ayant comme id celui passé en paramètre de l'URL
    //Renvoie Deleted si réussite, NOT FOUND si l'id n'est pas reconnu
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        if(postRepository.findById(id) == null)return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        postRepository.deleteById(postRepository.findById(id).getPostId());
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    //POST
    //Stocke l'image.jpeg sur le serveur puis créer et sauvegarde un nouveau Post en utilisant les données envoyé depuis le body de la requête.
    //Renvoie BAD REQUEST si le titre est null
    //Renvoie OK Save with success ! si succès
    @PostMapping(value = "/posts")
    public ResponseEntity<String> initPost(@RequestPart(value = "document", required = false)MultipartFile document, String title, String note) throws Exception {
        //Vérifie une image est envoyée
        if(document == null) return new ResponseEntity<>("Must send an image !",HttpStatus.BAD_REQUEST);
        //Check if document is img
        if(!Objects.equals(document.getContentType(), "image/jpeg")) return new ResponseEntity<>("Only JPEG !",HttpStatus.BAD_REQUEST);
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
        if(title == null) return new ResponseEntity<>("Title is null",HttpStatus.BAD_REQUEST);
        //Save Post
        postRepository.save(new Post(title,note,"localhost:9080/src/main/resources/uploads/"+orgName));
        //Download img
        document.transferTo(dest);
        return new ResponseEntity<>("Save with success !", HttpStatus.OK);
    }

    //PUT
    //Modifie le POST (seulement titre et note) ayant comme id l'id passé en paramètre de l'URL
    //Renvoie Modified, OK si succès
    @PutMapping(value = "/posts/{id}")
    public ResponseEntity<String> updatePost(@PathVariable long id, @RequestPart String title, String note) {
        if(title == null) {
            return new ResponseEntity<>("Le titre doit être renseigné", HttpStatus.BAD_REQUEST);
        }
        Post post = postRepository.findById(id);
        post.setTitle(title);
        post.setNote(note);
        postRepository.save(post);
        return new ResponseEntity<>("Modified", HttpStatus.OK);
    }

}