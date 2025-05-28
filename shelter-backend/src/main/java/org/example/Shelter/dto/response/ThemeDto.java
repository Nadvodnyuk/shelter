package org.example.Shelter.dto.response;

import org.example.Shelter.entity.Theme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDto {
    private List<Theme> themes;
}