package org.example.Shelter.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private long userL;
    private long animalL;
}
