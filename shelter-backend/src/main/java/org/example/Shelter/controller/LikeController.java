package org.example.Shelter.controller;

import org.example.Shelter.dto.request.LikeDto;
import org.example.Shelter.repository.LikeRepo;
import org.example.Shelter.service.LikeService;
import org.example.Shelter.service.facade.LikeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class LikeController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private LikeFacade likeFacade;

    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeRepo likeRepo;


    @PostMapping("/auth/likes/likeNum")
    public ResponseEntity<?> likeNum(@RequestParam long animal_id) {
        try {
            return ResponseEntity.ok(likeFacade.likeNumFacade(animal_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @PostMapping("/auth/likes/isLiked")
    public ResponseEntity<?> isLiked(@RequestParam long user_id, @RequestParam long animal_id) {
        try {
            return ResponseEntity.ok(likeFacade.isLikedBy(user_id, animal_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @PostMapping("/both/likes/postLike")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> postLike(@RequestBody LikeDto likeDto) {
        try {
            likeFacade.putLikeFacade(likeDto);
            return ResponseEntity.ok("Лайк успешно поставлен");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @DeleteMapping("/both/likes/{userL}/{animalL}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteLike(@PathVariable long userL,
                                        @PathVariable long animalL) {
        try {
            likeFacade.deleteLikeFacade(userL, animalL);
            return ResponseEntity.ok("Лайк успешно удален");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
