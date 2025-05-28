package org.example.Shelter.dto.response;


import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDto {
    private long animal_id;
    private String title;
    private String animal_text;
    private String image_url;
    private Integer like_num;
    private Integer comment_num;
    private java.sql.Timestamp publicationDate;
    private List<String> topics;

    public Timestamp getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Timestamp publicationDate) {
        this.publicationDate = publicationDate;
    }
}