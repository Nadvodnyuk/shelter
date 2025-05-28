package org.example.Shelter.service.facade;

import org.example.Shelter.dto.request.SignInDto;
import org.example.Shelter.dto.request.ThemesDto;
import org.example.Shelter.dto.response.SignInResponseDto;
import org.example.Shelter.exception.NotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Service;

@Service
public interface UserFacade {
    //Рега
    void registerFacade(@RequestParam String firstName,
                        @RequestParam String lastName,
                        @RequestParam String username,
                        @RequestParam String password) throws NotFoundException;

    //Вход
    SignInResponseDto authenticateFacade(@RequestBody SignInDto loginUserDto) throws NotFoundException;

    boolean authenticatedUserFacade();

    //Выход
    void logOutFacade(HttpServletRequest request, HttpServletResponse response);

    long deleteFacade(long user_id);

    void postThemesFacade (ThemesDto topicsRequest, Authentication authentication);
}
