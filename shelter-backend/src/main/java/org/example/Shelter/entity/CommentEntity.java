package org.example.Shelter.entity;

import javax.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long comment_id;

    @Column(name = "comment_text", length = 1000)
    private String comment_text;

    @Column(name = "comment_phone", length = 20)
    private String comment_phone;

    @Column(name = "comment_date")
    private java.sql.Timestamp commentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userC")
    private UserEntity userC;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animalC")
    private AnimalEntity animalC;


    @Override
    public String toString() {
        return "Comment [id=" + comment_id + ", date=" + commentDate + "]";
    }
}
