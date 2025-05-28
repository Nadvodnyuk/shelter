package org.example.Shelter.service.impl;

import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.CommentEntity;
import org.example.Shelter.entity.UserEntity;
import org.example.Shelter.repository.CommentRepo;
import org.example.Shelter.service.CommentService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    //Для вывода комментов
    public List<CommentEntity> getCommentsByAnimalIdWithPagination(AnimalEntity animal_с, int page) {
        // Вычисляем номер страницы и количество элементов на странице
        int pageNumber = page - 1; // Страницы начинаются с 0
        PageRequest pageRequest = PageRequest.of(pageNumber, 3);

        // Загружаем комментарии из базы данных с учетом пагинации
        return commentRepo.findByAnimalCOrderByCommentDateDesc(animal_с, pageRequest);
    }

    public int getCommentsByAnimalId(AnimalEntity animal_c) {
        List<CommentEntity> likeNum = commentRepo.findByAnimalC(animal_c);
        return likeNum.size();
    }

    //Для создания коммента
    public void createComment(UserEntity user_c, String comment_phone, String comment_text, AnimalEntity animal_с) {
        // Создаем коммент
        CommentEntity comment = new CommentEntity();

        // Устанавливаем автора, текст, дату публикации коммента
        comment.setUserC(user_c);
        comment.setComment_text(comment_text);
        comment.setComment_phone(comment_phone);
        comment.setAnimalC(animal_с);
        java.sql.Timestamp currentDateTime = java.sql.Timestamp.valueOf(LocalDateTime.now());
        comment.setCommentDate(currentDateTime);

        // Сохраняем статью в базе данных
        commentRepo.save(comment);
    }

    //Для удаления коммента:

    public void delete(Long comment_id) {
        commentRepo.deleteById(comment_id);
    }
}
