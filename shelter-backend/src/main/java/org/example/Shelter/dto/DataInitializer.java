package org.example.Shelter.dto;

import org.example.Shelter.entity.AnimalEntity;
import org.example.Shelter.entity.Gender;
import org.example.Shelter.entity.Theme;
import org.example.Shelter.repository.AnimalRepo;
import org.example.Shelter.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Autowired
    private AnimalRepo animalRepo;

    @Bean
    public CommandLineRunner loadData(AnimalService animalService) {
        return args -> {
            Instant now = Instant.now();
            Timestamp last24Hours = Timestamp.from(now.minus(24, ChronoUnit.HOURS));
            List<AnimalEntity> recentAnimals = animalRepo.findByPublicationDateAfter(last24Hours);
            if (recentAnimals.isEmpty()) {
                Set<Theme> themes1 = EnumSet.of(Theme.DOG);
                Set<Theme> themes2 = EnumSet.of(Theme.CAT);
                AnimalEntity animal1 = AnimalEntity.builder()
                        .title("Бакс")
                        .topics(themes1)
                        .gender(Gender.MALE)
                        .age(3)
                        .breed("Папильон")
                        .animal_text("     Активный, умный и верный. Обожает прогулки и игры. Ладит с людьми и собаками.\n" +
                                "\n" +
                                "     Знает команды, привит, кастрирован. Подойдёт активной семье.")
                        .image_url("/img/1-dog.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now.minus(60, ChronoUnit.MINUTES)))
                        .build();

                AnimalEntity animal2 = AnimalEntity.builder()
                        .title("Муся")
                        .topics(themes2)
                        .gender(Gender.FEMALE)
                        .age(2)
                        .breed("Британская короткошёрстная")
                        .animal_text("     Ласковая, спокойная, обожает сидеть на коленях.\n" +
                                "\n" +
                                "     Стерилизована, приучена к лотку, идеально подойдёт для семьи.")
                        .image_url("/img/4-cat.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now.minus(45, ChronoUnit.MINUTES)))
                        .build();

                AnimalEntity animal3 = AnimalEntity.builder()
                        .title("Лаки")
                        .topics(themes1)
                        .gender(Gender.MALE)
                        .age(4)
                        .breed("Метис")
                        .animal_text("     Добрый и немного застенчивый, но очень ласковый.\n" +
                                "\n" +
                                "     Идеален для квартиры, любит уют и спокойствие.")
                        .image_url("/img/2-dog.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now.minus(30, ChronoUnit.MINUTES)))
                        .build();

                AnimalEntity animal4 = AnimalEntity.builder()
                        .title("Тина")
                        .topics(themes2)
                        .gender(Gender.FEMALE)
                        .age(5)
                        .breed("Сиамская")
                        .animal_text("     Умная, независимая, спокойная.\n" +
                                "\n" +
                                "     Стерилизована, здорова, подойдёт взрослому человеку или пожилой паре.")
                        .image_url("/img/5-cat.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now.minus(15, ChronoUnit.MINUTES)))
                        .build();

                AnimalEntity animal5 = AnimalEntity.builder()
                        .title("Грей")
                        .topics(themes1)
                        .gender(Gender.MALE)
                        .age(2)
                        .breed("Афганская борзая")
                        .animal_text("     Энергичный, дружелюбный, обожает активные игры.\n" +
                                "\n" +
                                "     Нуждается в физической активности, привит, кастрирован.")
                        .image_url("/img/3-dog.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now))
                        .build();

                animalService.saveAnimals(Arrays.asList(animal1, animal2, animal3, animal4, animal5));
            }
        };
    }
}