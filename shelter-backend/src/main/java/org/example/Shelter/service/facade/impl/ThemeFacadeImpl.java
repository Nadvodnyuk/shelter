package org.example.Shelter.service.facade.impl;

import org.example.Shelter.dto.response.ThemeDto;
import org.example.Shelter.entity.Theme;
import org.example.Shelter.service.facade.ThemeFacade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ThemeFacadeImpl implements ThemeFacade {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ThemeDto getAllThemesFacade() {
        List<Theme> themes = Arrays.asList(Theme.values());
        return new ThemeDto(themes);
    }
}
