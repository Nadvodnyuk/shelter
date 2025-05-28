package org.example.Shelter.service.facade;

import org.example.Shelter.dto.request.CreateCommentDto;
import org.example.Shelter.dto.response.CommentDto;
import org.example.Shelter.exception.NotFoundException;

import java.util.List;
import org.springframework.stereotype.Service;
@Service
public interface CommentFacade {

    int commentNumFacade(long animal_id);

    //Вывести все комменты к статье
    List<CommentDto> getCommentsByAnimalIdWithPaginationFacade(long animal_c, int page)
            throws NotFoundException;

    //Написать коммент
    void createCommentFacade(long user_c, CreateCommentDto comment, long animal_с)
            throws NotFoundException;

    //Удалить коммент
    void deleteFacade(long comment_id);

}
