package org.example.Shelter.service.facade;

import org.example.Shelter.dto.response.GenderDto;
import org.springframework.stereotype.Service;

@Service
public interface GenderFacade {
    GenderDto getAllGendersFacade();
}
