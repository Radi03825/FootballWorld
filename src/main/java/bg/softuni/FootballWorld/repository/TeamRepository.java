package bg.softuni.FootballWorld.repository;

import bg.softuni.FootballWorld.model.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    List<TeamEntity> findAll();

    Optional<TeamEntity> findByName(String name);
}
