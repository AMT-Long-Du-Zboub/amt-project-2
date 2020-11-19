package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
    Collection<BadgeEntity> findByApplicationName(String ApplicationEntityApiKey);
}
