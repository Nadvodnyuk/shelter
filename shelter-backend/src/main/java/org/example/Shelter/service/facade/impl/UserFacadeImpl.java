package org.example.Shelter.service.facade.impl;

import org.example.Shelter.dto.request.SignInDto;
import org.example.Shelter.dto.request.ThemesDto;
import org.example.Shelter.dto.response.SignInResponseDto;
import org.example.Shelter.entity.Theme;
import org.example.Shelter.entity.UserEntity;
import org.example.Shelter.exception.NotFoundException;
import org.example.Shelter.service.JwtService;
import org.example.Shelter.service.UserService;
import org.example.Shelter.service.facade.UserFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    //С.Регистрация
    public void registerFacade(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String username,
                               @RequestParam String password) throws NotFoundException {
        UserEntity registeredUser = userService.registerUser(firstName, lastName, username, password);
    }

    //С.Вход
    public SignInResponseDto authenticateFacade(@RequestBody SignInDto loginUserDto) throws NotFoundException {
        UserEntity authenticatedUser = userService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        SignInResponseDto loginResponse = new SignInResponseDto();

        loginResponse.setUser_id(authenticatedUser. getUser_id());
        loginResponse.setName(authenticatedUser.getFirst_name()+" "+authenticatedUser.getLast_name());
        loginResponse.setRole(authenticatedUser.getRole());
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setFavoriteTopics(authenticatedUser.getFavoriteTopics());
        loginResponse.setBlockedTopics(authenticatedUser.getBlockedTopics());
        return loginResponse;
    }

    public boolean authenticatedUserFacade() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal()=="anonymousUser") {
            return false;}
        else
            return true;
    }

    //С.Выйти
    public void logOutFacade(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            jwtService.invalidateToken(token);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public long deleteFacade(long user_id) {
        return (userService.delete(user_id));
    }

    public void postThemesFacade (ThemesDto topicsRequest, Authentication authentication){
        List<String> favorite = topicsRequest.getFavorite();
        List<String> disliked = topicsRequest.getDisliked();

        System.out.println("Favorite topics: " + favorite);
        System.out.println("Disliked topics: " + disliked);
        Set<Theme> favoriteThemes = favorite.stream()
                .map(Theme::valueOf)
                .collect(Collectors.toSet());
        Set<Theme> dislikedThemes = disliked.stream()
                .map(Theme::valueOf)
                .collect(Collectors.toSet());
        userService.postThemesService(favoriteThemes, dislikedThemes, authentication);
    }
}
