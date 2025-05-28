package org.example.Shelter.service.facade;

import org.example.Shelter.dto.request.LikeDto;
import org.springframework.stereotype.Service;
@Service
public interface LikeFacade {
    //C.Вывести лайки для статьи
    int likeNumFacade(long animal_id);

    boolean isLikedBy(long user_id, long animal_id);

    ///Для авторизованного:
    //C.Поставить лайк
    void putLikeFacade(LikeDto likeDto);

    //C.Удалить лайк
    void deleteLikeFacade(long userL, long animalL);
}
