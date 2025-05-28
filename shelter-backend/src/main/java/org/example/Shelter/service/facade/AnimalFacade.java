package org.example.Shelter.service.facade;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.example.Shelter.dto.request.CreateAnimalDto;
import org.example.Shelter.dto.request.EditAnimalDto;
import org.example.Shelter.dto.response.AnimalDto;
import org.example.Shelter.exception.NotFoundException;

import java.util.List;

@Service
public interface AnimalFacade {
    //Для списка статей:
    List<AnimalDto> getAllLatestAnimalsFacade();
    //Для создания статьи:

    void createAnimalFacade(CreateAnimalDto createAnimalDto);

    //Для получения одной статьи:
    AnimalDto getOneFacade(Long art_id) throws NotFoundException;

    //Для изменения статьи
    void editAnimalFacade(EditAnimalDto editAnimalDto, long animal_id);

    //Удаление статьи:
    void deleteFacade(Long art_id);

    List<AnimalDto> getAnimalsByUserPreferencesFacade(Authentication authentication);
}
