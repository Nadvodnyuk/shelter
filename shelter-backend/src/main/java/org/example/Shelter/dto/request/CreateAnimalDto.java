package org.example.Shelter.dto.request;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnimalDto {
    private String title;
    private String animal_text;
    private String image_url;
    private List<String> topics;
}