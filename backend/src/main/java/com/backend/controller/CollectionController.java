package com.backend.controller;

import com.backend.entity.Collection;
import com.backend.service.CollectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@CrossOrigin(origins = "*")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController (CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        return ResponseEntity.ok(
                collectionService.getAllCollections()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollection
            (@PathVariable int id) {
        Collection collection = collectionService.getCollectionById(id);

        if(collection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collection);
    }


    @PostMapping
    public ResponseEntity<Collection> createCollection(@RequestBody Collection collection) {
        collectionService.addCollection(collection);
        return ResponseEntity.status(HttpStatus.CREATED).body(collection);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Collection> updateCollection(
            @PathVariable int id, @RequestBody Collection collection
    ){
        boolean success =
                collectionService.updateCollection(id,collection);
        if(!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collectionService.getCollectionById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(
            @PathVariable int id
    ){
        boolean deleted = collectionService.deleteCollection(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
