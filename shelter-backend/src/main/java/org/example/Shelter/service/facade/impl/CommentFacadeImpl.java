package org.example.Shelter.service.facade.impl;

import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.CommentEntity;
import org.example.Shelter.entity.UserEntity;
import org.example.Shelter.exception.NotFoundException;
import org.example.Shelter.service.CommentService;
import org.example.Shelter.service.AnimalService;
import org.example.Shelter.repository.CommentRepo;
import org.example.Shelter.service.UserService;
import org.example.Shelter.service.facade.CommentFacade;
import org.example.Shelter.dto.request.CreateCommentDto;
import org.example.Shelter.dto.response.CommentDto;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class CommentFacadeImpl implements CommentFacade {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    //Количество комментриев

    public int commentNumFacade(long animal_id) {
        try {
            AnimalEntity art = animalService.getOne(animal_id);
            return commentService.getCommentsByAnimalId(art);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Вывести все комменты к статье
    public List<CommentDto> getCommentsByAnimalIdWithPaginationFacade(long animal_c, int page) throws NotFoundException {
        AnimalEntity art = animalService.getOne(animal_c);
        List<CommentEntity> threeCommentsEntity = commentService.getCommentsByAnimalIdWithPagination(art, page);

        for (CommentEntity comment : threeCommentsEntity) {
            Hibernate.initialize(comment.getUserC());
        }

        List<CommentDto> threeComments = threeCommentsEntity.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
        System.out.println(threeComments);
        return threeComments;
    }

    //Написать коммент к статье
    //С.Написать коммент
    public void createCommentFacade(long user_c, CreateCommentDto comment, long animal_c) throws NotFoundException {
        AnimalEntity art = animalService.getOne(animal_c);
        UserEntity user = userService.getOne(user_c);
        commentService.createComment(user, comment.getComment_phone(), comment.getComment_text(), art);
    }

    //Удалить коммент для админа
    //С.Удалить комм
    public void deleteFacade(long comment_id) {
        commentService.delete(comment_id);
    }
}
