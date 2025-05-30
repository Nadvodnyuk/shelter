package org.example.Shelter.service;

import org.example.Shelter.entity.CommentEntity;
import org.example.Shelter.entity.UserEntity;
import org.example.Shelter.entity.AnimalEntity;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    List<CommentEntity> getCommentsByAnimalIdWithPagination(AnimalEntity animal_c, int page);

    int getCommentsByAnimalId(AnimalEntity animal_c);
    void createComment(UserEntity user_c, String comment_phone, String comment_text, AnimalEntity animal_c);

    void delete(Long comment_id);

}
