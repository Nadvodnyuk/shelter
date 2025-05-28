package org.example.Shelter.service.facade;

import org.example.Shelter.dto.response.ThemeDto;
import org.springframework.stereotype.Service;

@Service
public interface ThemeFacade {
    ThemeDto getAllThemesFacade();
}
