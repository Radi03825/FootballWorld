package bg.softuni.FootballWorld.repository;

import bg.softuni.FootballWorld.model.entity.CommentEntity;
import bg.softuni.FootballWorld.model.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByPlayer(PlayerEntity player);
}
