package org.example.Shelter.service;

import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.Theme;
import org.example.Shelter.exception.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service

public interface AnimalService {

    List<AnimalEntity> getAllLatestAnimals();

    void createAnimal(String title, String text, String imageUrl, Set<Theme> topics);

    AnimalEntity getOne(Long art_id) throws NotFoundException;

    void editAnimal(long animal_id, String title, String text, String imageUrl, Set<Theme> topics);

    void delete(Long art_id);

    void saveAnimals(List<AnimalEntity> animals);

    List<AnimalEntity> getFilteredAnimalsByUserPreferences(Authentication authentication);
}
