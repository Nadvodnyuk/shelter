package org.example.Shelter.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogOutDto {
    private String token;
    private long expiresIn;
}
