package org.example.Shelter.dto;

import org.example.Shelter.entity.AnimalEntity;
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
                        .title("Жара в Петербурге во вторник преодолела отметку в 25 градусов уже к 9 утра")
                        .topics(themes1)
                        .animal_text("     Температура воздуха в Петербурге 28 мая уже к 9 часам утра преодолела отметку в 25 градусов. Об этом в своём Telegram-канале заявил главный синоптик городского Гидрометцентра Александр Колесов.\n" +
                                "\n" +
                                "— Вчера в городе температура воздуха не дотянула одной десятой до +28 гр. А сегодня и +29 гр. может быть. Так что терпим, в выходные должно отпустить немного, — отметил метеоролог.\n" +
                                "При этом в городе возможны кратковременные дожди, которые немного «собьют» температуру. Также не исключена гроза.\n" +
                                "\n" +
                                "Ленинградскую область дожди точно не обойдут стороной, ожидаются даже ливни с грозами и градом, но все они будут локальны.\n" +
                                "\n" +
                                "Напомним, ранее синоптик Колесов пообещал петербуржцам аномально жаркую погоду.")
                        .image_url("/img/1-dog.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now.minus(60, ChronoUnit.MINUTES)))
                        .build();

                AnimalEntity animal2 = AnimalEntity.builder()
                        .title("В Петербурге начался сезон клещей")
                        .topics(themes2)
                        .animal_text("     В Санкт-Петербург весна не приходит по календарю: более-менее теплая погода обычно устанавливается только в мае. Горожане отправляются гулять в парки, едут на дачи, а в праздничные и выходные дни — за город. С приходом тепла активизируются насекомые, по прогнозам, именно на май придется сезон клещей в Санкт-Петербурге в 2024 году.")
                        .image_url("/img/4-cat.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now.minus(45, ChronoUnit.MINUTES)))
                        .build();

                AnimalEntity animal3 = AnimalEntity.builder()
                        .title("Всё о параде Международного дня защиты детей")
                        .topics(themes1)
                        .animal_text("     Официальным годом рождения праздника считается 1949-й. Именно в ноябре того года в Париже состоялась специальная сессия Международной демократической федерации женщин, на которой приняли решение учредить День защиты детей. А отмечать праздник начали уже со следующего года: в 1950-м торжественные мероприятия прошли в 51 государстве мира.\n" +
                                "\n" +
                                "       Но почему в 1949 году возникла такая необходимость — учредить праздник, посвященный детям? Дело в том, что после Второй мировой войны положение несовершеннолетних в разных странах мира было ужасно: многие из них лишились родителей и крова, поэтому были вынуждены жить на улице, попрошайничать или воровать. Кто-то из малышей умирал от голода, кто-то — от болезней. Конечно, такая ситуация не могла не привлечь внимание неравнодушных людей.")
                        .image_url("/img/2-dog.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now.minus(30, ChronoUnit.MINUTES)))
                        .build();

                AnimalEntity animal4 = AnimalEntity.builder()
                        .title("В Петербурге в июне ожидается начало сезона дождей")
                        .topics(themes2)
                        .animal_text("     В ближайшие дни петербуржцам и гостям Северной столицы стоит подготовиться к жаре. По данным главы городского Росгидромета Александра Колесова, начавшаяся неделя будет очень жаркой и с каждым днём температура воздуха будет становиться всё выше. Об этом он написал в своём Telegram-канале.\n" +
                                "\n" +
                                "       Главный синоптик Северной столицы отметил, что прошедший понедельник, 27 мая, стал теплее, чем прошлая пятница, 24-е, с максимальной температурой +27,2 градуса. Он добавляет, что перекрыть рекорд 1958 года (+30,9 градуса) «нереально», однако максимальная температура 27 мая всё-таки смогла превзойти значения прошлого века – 1995 и 2009 годов (по +26 градусов), а затем и позапрошлого – 1887 и 1889 годов (выше +27 градусов).\n" +
                                "\n" +
                                "       Пока остался один непобитый температурный рекорд – 1891 года, со значением дневной температуры +29,8 градуса.")
                        .image_url("/img/5-cat.jpg")
                        .like_num(0)
                        .comment_num(0)
                        .publicationDate(Timestamp.from(now.minus(15, ChronoUnit.MINUTES)))
                        .build();

                AnimalEntity animal5 = AnimalEntity.builder()
                        .title("В Петербурге 60% пастеризованного молока разбавляют водой")
                        .topics(themes1)
                        .animal_text("     Во многих странах 1 июня будет отмечаться Всемирный день молока. Накануне этого праздника организация «Общественный контроль» прошлась по магазинам Петербурга и выбрала 11 упаковок пастеризованного молока, чтобы провести исследование. И результаты проверки оказались плачевными. Семь марок не соответствовали ГОСТу. В частности, производители разбавляли этот напиток водой и экономили на молочном жире. Кроме того, в одной из бутылок была найдена кишечная палочка.\n" +
                                "\n" +
                                "-      Удручающие результаты экспертизы оказались в этом году: лишь четыре из 11 образцов молока, отправленных на исследования, подтвердили соответствие обязательным требованиям государственного стандарта, - говорится в сообщении.\n" +
                                "\n")
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
