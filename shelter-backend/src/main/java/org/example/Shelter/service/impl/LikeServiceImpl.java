package org.example.Shelter.service.impl;

import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.LikeEntity;
import org.example.Shelter.entity.UserEntity;
import org.example.Shelter.repository.LikeRepo;
import org.example.Shelter.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeRepo likeRepo;

    public int getLikesByAnimalId(AnimalEntity animal_l) {
        // Поменяла на "найти количество лайков", не знаю, нужно или нет, можно потом вернуть
        List<LikeEntity> likeNum = likeRepo.findByAnimalL(animal_l);
        return likeNum.size();
    }

    public int getLikesByUserId(UserEntity user_l) {
        List<LikeEntity> likeNum = likeRepo.findByUserL(user_l);
        return likeNum.size();
    }

    public boolean isLikedByUserAndAnimal(UserEntity user_l, AnimalEntity animal_l) {
        return likeRepo.existsByAnimalLAndUserL(animal_l, user_l);
    }

    public void createLike(UserEntity user_l, AnimalEntity animal_l) {
        System.out.println("CreateLike ызван");
        if (isLikedByUserAndAnimal(user_l, animal_l)) {
            System.out.println("Лайк пользователя для данной статьи уже существует");
        } else {
            LikeEntity like = new LikeEntity();
            // Устанавливаем автора, текст, дату публикации коммента
            like.setUserL(user_l);
            like.setAnimalL(animal_l);
            // Сохраняем статью в базе данных
            likeRepo.save(like);
        }
    }

    public void delete(UserEntity user_l, AnimalEntity animal_l) {
        likeRepo.deleteByAnimalLAndUserL(animal_l, user_l);
    }
}
