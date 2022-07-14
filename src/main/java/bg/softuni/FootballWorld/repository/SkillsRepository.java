package bg.softuni.FootballWorld.repository;

import bg.softuni.FootballWorld.model.entity.SkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepository extends JpaRepository<SkillsEntity, Long> {
}
