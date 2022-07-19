package bg.softuni.FootballWorld.repository;

import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    //select p.* from players as p join skills as s on p.skills_id = s.id where position="DEFENDER"
    //order by (s.pace + s.shooting + s.passing + s.defending) desc limit 3;
    @Query("SELECT p FROM PlayerEntity p WHERE p.position = :position ORDER BY (p.skills.pace + p.skills.shooting + p.skills.passing + p.skills.defending) DESC")
    List<PlayerEntity> findByPosition(@Param("position") PositionEnum position);
}
