package org.example.Shelter.dto.response;

import org.example.Shelter.dto.request.UserDto;
import lombok.Data;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long comment_id;
    private String comment_phone;
    private String comment_text;
    private java.sql.Timestamp commentDate;
    private UserDto userC;
}

