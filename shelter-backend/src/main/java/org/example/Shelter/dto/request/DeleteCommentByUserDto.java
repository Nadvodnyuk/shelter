package org.example.Shelter.dto.request;

import org.example.Shelter.entity.UserEntity;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCommentByUserDto {
    private long comment_id;
    private UserEntity userC;
}
