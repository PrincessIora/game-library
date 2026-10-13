package com.backend.controller;

import com.backend.entity.Platform;
import com.backend.service.PlatformService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platforms")
@CrossOrigin(origins = "*")
public class PlatformController {
    private PlatformService platformService;

    public PlatformController(PlatformService platformService) {
        this.platformService = platformService;
    }
    @GetMapping
    public ResponseEntity<List<Platform>> getAllPlatforms() {
        return ResponseEntity.ok(
                platformService.findAllPlatforms()
        );
    }

   @GetMapping("/{id}")
    public ResponseEntity<Platform> getPlatform(
            @PathVariable int id) {
        Platform platform = platformService.getPlatformById(id);

        if(platform == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(platform);
   }

   @PostMapping
   public ResponseEntity<Platform> createPlatform(
           @RequestBody Platform platform
   ){
        platformService.addPlatform(platform);

        return ResponseEntity.status(HttpStatus.CREATED).body(platform);
   }

    @PutMapping("/{id}")
    public ResponseEntity<Platform> updatePlatform(
            @PathVariable int id,
            @RequestBody Platform platform
    ){
        boolean success = platformService.updatePlatform(id, platform);

        if(!success) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(platformService.getPlatformById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Platform> deletePlatform(
            @PathVariable int id
    ){
        boolean deleted = platformService.deletePlatform(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
