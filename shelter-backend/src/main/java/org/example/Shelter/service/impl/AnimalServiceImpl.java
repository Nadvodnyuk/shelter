package org.example.Shelter.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.example.Shelter.entity.Gender;
import org.example.Shelter.entity.Theme;
import org.example.Shelter.entity.UserEntity;
import org.example.Shelter.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.exception.NotFoundException;
import org.example.Shelter.repository.AnimalRepo;
import org.example.Shelter.service.AnimalService;

@Service
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalRepo animalRepo;

    @Autowired
    private UserRepo userRepo;

    //Для списка статей:
    public List<AnimalEntity> getAllLatestAnimals() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusWeeks(1);
        java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(twentyFourHoursAgo);
        return animalRepo.findByPublicationDateAfter(timestamp);
    }

    //Для создания статьи:

    public void createAnimal(String title, String text, String imageUrl, Set<Theme> topics, Gender gender, String breed, Integer age) {
        AnimalEntity animal = new AnimalEntity();

        animal.setTitle(title);
        animal.setAnimal_text(text);
        animal.setImage_url(imageUrl);
        java.sql.Timestamp currentDateTime = java.sql.Timestamp.valueOf(LocalDateTime.now());
        animal.setPublicationDate(currentDateTime);
        animal.setTopics(topics);
        animal.setGender(gender);
        animal.setBreed(breed);
        animal.setAge(age);

        animalRepo.save(animal);
    }

    //Для получения одной статьи:
    public AnimalEntity getOne(Long art_id) throws NotFoundException {
        return animalRepo.findById(art_id)
                .orElseThrow(() -> new NotFoundException("Животное с id " + art_id + " не найдено"));
    }

    //Для изменения статьи
    public void editAnimal(long animal_id, String title, String text, String imageUrl, Set<Theme> topics, Gender gender, String breed, Integer age) {
        AnimalEntity animal = animalRepo.findById(animal_id).get();

        animal.setTitle(title);
        animal.setAnimal_text(text);
        animal.setImage_url(imageUrl);
        java.sql.Timestamp currentDateTime = java.sql.Timestamp.valueOf(LocalDateTime.now());
        animal.setPublicationDate(currentDateTime);
        animal.setTopics(topics);
        animal.setGender(gender);
        animal.setBreed(breed);
        animal.setAge(age);

        animalRepo.save(animal);
    }


    public List<AnimalEntity> getFilteredAnimalsByUserPreferences(Authentication authentication) {
        List<AnimalEntity> animalsLast24Hours = getAllLatestAnimals();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Optional<UserEntity> userOptional = userRepo.findByUsername(username);

            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                Set<Theme> favoriteTopics = user.getFavoriteTopics();
                Set<Theme> blockedTopics = user.getBlockedTopics();

                List<AnimalEntity> filteredAnimals = animalsLast24Hours.stream()
                        .filter(animal -> {
                            boolean containsBlockedTopic = animal.getTopics().stream()
                                    .anyMatch(blockedTopics::contains);
                            return !containsBlockedTopic;
                        })
                        .sorted((a1, a2) -> {
                            boolean hasFavoriteTopicA1 = a1.getTopics().stream().anyMatch(favoriteTopics::contains);
                            boolean hasFavoriteTopicA2 = a2.getTopics().stream().anyMatch(favoriteTopics::contains);

                            if (hasFavoriteTopicA1 && !hasFavoriteTopicA2) {
                                return -1;
                            } else if (!hasFavoriteTopicA1 && hasFavoriteTopicA2) {
                                return 1;
                            } else {
                                return a2.getPublicationDate().compareTo(a1.getPublicationDate());
                            }
                        })
                        .collect(Collectors.toList());

                return filteredAnimals;
            }
        }

        animalsLast24Hours.sort((a1, a2) -> a2.getPublicationDate().compareTo(a1.getPublicationDate()));
        return animalsLast24Hours;
    }


    //Удаление статьи:
    public void delete(Long art_id) {
        animalRepo.deleteById(art_id);
    }

    public void saveAnimals(List<AnimalEntity> animals) {
        animalRepo.saveAll(animals);
    }
}
