package org.example.Shelter.repository;

import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.LikeEntity;
import java.util.List;
import org.example.Shelter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LikeRepo extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findByAnimalL(AnimalEntity animal_id);
    List<LikeEntity> findByUserL(UserEntity user_id);

    boolean existsByAnimalLAndUserL(AnimalEntity animalL, UserEntity userL);

    @Transactional(rollbackFor = Exception.class)
    void deleteByAnimalLAndUserL(AnimalEntity animal_id, UserEntity user_id);
}