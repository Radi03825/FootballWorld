package bg.softuni.FootballWorld.repository;

import bg.softuni.FootballWorld.model.entity.StadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StadiumRepository extends JpaRepository<StadiumEntity, Long> {
    Optional<StadiumEntity> findByName(String name);
}
