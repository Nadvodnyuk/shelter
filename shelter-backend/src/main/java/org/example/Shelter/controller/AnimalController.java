package org.example.Shelter.controller;

import org.example.Shelter.dto.request.CreateAnimalDto;
import org.example.Shelter.dto.request.EditAnimalDto;
import org.example.Shelter.exception.NotFoundException;
import org.example.Shelter.service.facade.AnimalFacade;
import org.springframework.security.access.prepost.PreAuthorize;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class AnimalController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AnimalFacade animalFacade;


    @GetMapping("/auth/animals/all")
    public ResponseEntity<?> getAllAnimals(Authentication authentication) {
        try {
            return ResponseEntity.ok(animalFacade.getAnimalsByUserPreferencesFacade(authentication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @PostMapping("/admin/animals/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAnimal(@RequestBody CreateAnimalDto animal) {
        try {
            animalFacade.createAnimalFacade(animal);
            return ResponseEntity.ok("Статья успешно сохранена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @GetMapping("/admin/animals/{animal_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAnimalById(@PathVariable long animal_id) {
        try {
            return ResponseEntity.ok(animalFacade.getOneFacade(animal_id));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @PutMapping("/admin/animals/update/{animal_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAnimal(@PathVariable long animal_id,
                                           @RequestBody EditAnimalDto animal) {
        try {
            animalFacade.editAnimalFacade(animal, animal_id);
            return ResponseEntity.ok("Статья успешно обновлена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


    @DeleteMapping("/admin/animals/{animal_id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAnimal(@PathVariable long animal_id) {
        try {
            animalFacade.deleteFacade(animal_id);
            return ResponseEntity.ok("Статья успешно удалена");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
