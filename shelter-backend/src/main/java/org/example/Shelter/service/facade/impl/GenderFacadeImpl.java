package org.example.Shelter.service.facade.impl;

import org.example.Shelter.dto.response.GenderDto;
import org.example.Shelter.entity.Gender;
import org.example.Shelter.service.facade.GenderFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class GenderFacadeImpl implements GenderFacade {
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public GenderDto getAllGendersFacade() {
        List<Gender> genders = Arrays.asList(Gender.values());
        return new GenderDto(genders);
    }
}
