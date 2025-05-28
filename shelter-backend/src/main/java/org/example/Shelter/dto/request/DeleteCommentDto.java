package org.example.Shelter.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCommentDto {
    private long comment_id;
}