package org.example.Shelter.dto.response;

import org.example.Shelter.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenderDto {
    private List<Gender> gender;
}