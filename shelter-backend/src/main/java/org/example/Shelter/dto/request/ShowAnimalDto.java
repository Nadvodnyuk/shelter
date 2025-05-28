package org.example.Shelter.dto.request;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowAnimalDto {
    private java.sql.Timestamp publicationDate;
}
