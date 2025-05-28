package org.example.Shelter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "animals")
public class AnimalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long animal_id;

    @Column(name = "title")
    private String title;

    @ElementCollection
    @CollectionTable(name = "animal_topics", joinColumns = @JoinColumn(name = "animal_id"))
    @Column(name = "topic")
    private Set<Theme> topics;

    @Column(name = "animal_text", columnDefinition = "TEXT")
    private String animal_text;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "like_num")
    private Integer like_num;

    @Column(name = "comment_num")
    private Integer comment_num;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "publicationDate")
    private java.util.Date publicationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animalC")
    @JsonIgnore
    private List<CommentEntity> comments;

    @Override
    public String toString() {
        return "User [id=" + animal_id + ", title=" + title +
                ", animal text=" + animal_text + ", image=" + image_url +
                ", number of likes=" + like_num + ", number of comments=" + comment_num +
                ", publication date=" + publicationDate + "]";
    }
}

