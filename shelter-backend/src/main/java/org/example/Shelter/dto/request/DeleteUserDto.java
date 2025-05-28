package org.example.Shelter.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserDto {
    private long user_id;
}
