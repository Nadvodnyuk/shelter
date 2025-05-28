package org.example.Shelter.dto.request;

import lombok.*;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditAnimalDto {
    private long animal_id;
    private String title;
    private String animal_text;
    private String image_url;
    private List<String> topics;
}
