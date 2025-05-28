package org.example.Shelter.controller;

import org.example.Shelter.service.AnimalService;
import org.example.Shelter.service.facade.ThemeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class ThemeController {

    @Autowired
    private AnimalService animalService;
    @Autowired
    private ThemeFacade themeFacade;

    @GetMapping("/both/themes")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllThemes() {
        try{
            return ResponseEntity.ok(themeFacade.getAllThemesFacade());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
