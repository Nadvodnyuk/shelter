package org.example.Shelter.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String first_name;
    private String last_name;
}
