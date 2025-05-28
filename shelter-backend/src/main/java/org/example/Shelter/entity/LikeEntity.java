package org.example.Shelter.entity;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "likes")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long like_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userL")
    private UserEntity userL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "animalL")
    private AnimalEntity animalL;


    @Override
    public String toString() {
        return "User [id=" + like_id + "]";
    }
}
