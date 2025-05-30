package org.example.Shelter.controller;

import org.example.Shelter.service.AnimalService;
import org.example.Shelter.service.facade.GenderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class GenderController {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private GenderFacade genderFacade;

    @GetMapping("/both/gender")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllGenders() {
        try{
            return ResponseEntity.ok(genderFacade.getAllGendersFacade());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
