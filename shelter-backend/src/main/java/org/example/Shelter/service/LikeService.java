package org.example.Shelter.service;

import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    int getLikesByAnimalId(AnimalEntity animal_l);

    boolean isLikedByUserAndAnimal(UserEntity user_l, AnimalEntity animal_l);

    void createLike(UserEntity user_l, AnimalEntity animal_l);

    void delete(UserEntity user_l, AnimalEntity animal_l);
}
