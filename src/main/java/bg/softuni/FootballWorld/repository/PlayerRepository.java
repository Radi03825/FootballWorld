package bg.softuni.FootballWorld.repository;

import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    @Query("SELECT p FROM PlayerEntity p WHERE p.position = :position ORDER BY (p.skills.pace + p.skills.shooting + p.skills.passing + p.skills.defending) DESC")
    Page<PlayerEntity> findByPosition(@Param("position") PositionEnum position, Pageable pageable);
}
