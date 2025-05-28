package org.example.Shelter.dto.response;

import org.example.Shelter.entity.Role;
import org.example.Shelter.entity.Theme;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInResponseDto {
    private long user_id;
    private String name;
    private Role role;
    private String token;
    private long expiresIn;
    private Set<Theme> favoriteTopics;
    private Set<Theme> blockedTopics;
}
