package org.example.Shelter.controller;

import org.example.Shelter.dto.request.CreateCommentDto;
import org.example.Shelter.exception.NotFoundException;
import org.example.Shelter.service.CommentService;
import org.example.Shelter.service.facade.CommentFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class CommentController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentFacade commentFacade;

    //ок
    @PostMapping("/auth/comments/commentNum")
    public ResponseEntity<?> commentNum(@RequestParam long animal_id) {
        try {
            return ResponseEntity.ok(commentFacade.commentNumFacade(animal_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    //ок
    @GetMapping("/auth/comments/showComments/{animal_id}/{page}")
    public ResponseEntity<?> getCommentsForAnimal(@PathVariable long animal_id,
                                                   @PathVariable int page) {
        try {
            return ResponseEntity.ok(commentFacade.getCommentsByAnimalIdWithPaginationFacade(animal_id, page));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/user/comments/showCommentsUser/{user_id}")
    public ResponseEntity<?> getCommentsForUser(@PathVariable long user_id) {
        try {
            return ResponseEntity.ok(commentFacade.getCommentsByUserId(user_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    //ок
    @PostMapping("/both/comments/postComment")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> postComment(
            @RequestParam long user_id,
            @RequestParam long animal_id,
                                        @RequestBody CreateCommentDto comment) {
        try {
            commentFacade.createCommentFacade(user_id, comment, animal_id);
            return ResponseEntity.ok("Комментарий успешно сохранен");
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    //ок
    @DeleteMapping("/admin/comments/{comment_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteComment(@PathVariable long comment_id) {
        try {
            commentFacade.deleteFacade(comment_id);
            return ResponseEntity.ok("Комментарий успешно удален");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
