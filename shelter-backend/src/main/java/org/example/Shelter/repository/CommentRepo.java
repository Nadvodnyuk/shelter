package org.example.Shelter.repository;

import java.util.List;
import org.example.Shelter.entity.CommentEntity;
import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.UserEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByAnimalC(AnimalEntity animal_id, PageRequest pageRequest);
    List<CommentEntity> findByUserC(UserEntity user_id);

    List<CommentEntity> findByAnimalCOrderByCommentDateDesc(AnimalEntity animal_id, PageRequest pageRequest);

    List<CommentEntity> findByAnimalC(AnimalEntity animal_id);
}