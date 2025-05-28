package org.example.Shelter.dto.request;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentDto {
    private String comment_phone;
    private String comment_text;
}
