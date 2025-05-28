package org.example.Shelter.service;


import org.example.Shelter.dto.request.SignInDto;
import org.example.Shelter.entity.Theme;
import org.example.Shelter.entity.UserEntity;
import org.example.Shelter.exception.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service

public interface UserService {
    UserEntity registerUser(String firstName, String lastName, String username, String password);

    UserEntity authenticate(SignInDto input) throws RuntimeException;

    UserEntity getOne(Long art_id) throws NotFoundException;

    List<UserEntity> allUsers();

    long delete(Long user_id);

    void postThemesService(Set<Theme> favoriteThemes, Set<Theme> dislikedThemes, Authentication authentication);
}
