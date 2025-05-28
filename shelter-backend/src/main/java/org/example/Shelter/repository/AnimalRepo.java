package org.example.Shelter.repository;

import org.example.Shelter.entity.AnimalEntity;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepo extends JpaRepository<AnimalEntity, Long> {
    List<AnimalEntity> findByPublicationDateAfter(Timestamp date);
}