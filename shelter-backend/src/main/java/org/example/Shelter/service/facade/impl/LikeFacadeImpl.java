package org.example.Shelter.service.facade.impl;

import org.example.Shelter.dto.request.LikeDto;
import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.UserEntity;
import org.example.Shelter.exception.NotFoundException;
import org.example.Shelter.service.AnimalService;
import org.example.Shelter.service.LikeService;
import org.example.Shelter.service.UserService;
import org.example.Shelter.service.facade.LikeFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeFacadeImpl implements LikeFacade {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    //C.Вывести кол-во лайков
    public int likeNumFacade(long animal_id) {

        try {
            AnimalEntity art = animalService.getOne(animal_id);
            return likeService.getLikesByAnimalId(art);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Лайкнутые посты пользователем
    public boolean isLikedBy(long user_id, long animal_id) {
        try {
            AnimalEntity art = animalService.getOne(animal_id);
            UserEntity user = userService.getOne(user_id);
            return likeService.isLikedByUserAndAnimal(user, art);

        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ///Для авторизованного:
    //C.Поставить лайк
    public void putLikeFacade(LikeDto likeDto){
        try {
            AnimalEntity art = animalService.getOne(likeDto.getAnimalL());
            UserEntity user = userService.getOne(likeDto.getUserL());
            likeService.createLike(user, art);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //C.Удалить лайк
    public void deleteLikeFacade(long userL, long animalL){
        try {
            AnimalEntity art = animalService.getOne(animalL);
            UserEntity user = userService.getOne(userL);
            likeService.delete(user, art);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
