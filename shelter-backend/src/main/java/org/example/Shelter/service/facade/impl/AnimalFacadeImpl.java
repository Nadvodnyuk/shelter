package org.example.Shelter.service.facade.impl;

import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.Gender;
import org.example.Shelter.entity.Theme;
import org.example.Shelter.exception.NotFoundException;
import org.example.Shelter.service.AnimalService;
import org.example.Shelter.service.facade.AnimalFacade;
import org.example.Shelter.dto.request.CreateAnimalDto;
import org.example.Shelter.dto.request.EditAnimalDto;
import org.example.Shelter.dto.response.AnimalDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnimalFacadeImpl implements AnimalFacade {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private ModelMapper modelMapper;

    //Для списка статей:
    public List<AnimalDto> getAllLatestAnimalsFacade() {
        List<AnimalEntity> animals = animalService.getAllLatestAnimals();
        Type listType = new TypeToken<List<AnimalDto>>() {
        }.getType();
        List<AnimalDto> AnimalsDto = new ModelMapper().map(animals, listType);
        return AnimalsDto;
    }

    //Для создания статьи:

    public void createAnimalFacade(CreateAnimalDto createAnimalDto) {
        Set<Theme> themes = createAnimalDto.getTopics().stream()
                .map(Theme::valueOf)
                .collect(Collectors.toSet());
        Gender gender = Gender.valueOf(createAnimalDto.getGender());
        animalService.createAnimal(createAnimalDto.getTitle(),
                createAnimalDto.getAnimal_text(),
                createAnimalDto.getImage_url(),
                themes,
                gender,
                createAnimalDto.getBreed(),
                createAnimalDto.getAge());
    }

    //Для получения одной статьи:
    public AnimalDto getOneFacade(Long art_id) throws NotFoundException {
        AnimalEntity art = animalService.getOne(art_id);
        AnimalDto oneAnimal = modelMapper.map(art, AnimalDto.class);
        return oneAnimal;
    }

    //Для изменения статьи
    public void editAnimalFacade(EditAnimalDto editAnimalDto,  long animal_id) {
        editAnimalDto.setAnimal_id(animal_id);
        Set<Theme> themes = editAnimalDto.getTopics().stream()
                .map(Theme::valueOf)
                .collect(Collectors.toSet());
        System.out.println("themes"+themes);
        Gender gender = Gender.valueOf(editAnimalDto.getGender());
        animalService.editAnimal(editAnimalDto.getAnimal_id(),
                editAnimalDto.getTitle(),
                editAnimalDto.getAnimal_text(),
                editAnimalDto.getImage_url(),
                themes,
                gender,
                editAnimalDto.getBreed(),
                editAnimalDto.getAge());
    }

    //Удаление статьи:
    public void deleteFacade(Long art_id) {
        animalService.delete(art_id);
    }


    public List<AnimalDto> getAnimalsByUserPreferencesFacade(Authentication authentication) {
        List<AnimalEntity> animals = animalService.getFilteredAnimalsByUserPreferences(authentication);
        Type listType = new TypeToken<List<AnimalDto>>() {}.getType();
        List<AnimalDto> AnimalsDto = new ModelMapper().map(animals, listType);
        return AnimalsDto;
    }
}