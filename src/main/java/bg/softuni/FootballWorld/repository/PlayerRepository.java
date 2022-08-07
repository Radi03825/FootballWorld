package bg.softuni.FootballWorld.repository;

import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import bg.softuni.FootballWorld.model.entity.enums.PositionEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>, JpaSpecificationExecutor<PlayerEntity> {

    @Query("SELECT p FROM PlayerEntity p WHERE p.position = :position ORDER BY (p.skills.pace + p.skills.shooting + p.skills.passing + p.skills.defending) DESC")
    Page<PlayerEntity> findByPosition(@Param("position") PositionEnum position, Pageable pageable);

    List<PlayerEntity> findAllByTeamId(Long team_id);
}
