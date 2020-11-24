package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.BadgeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface BadgeRepository extends CrudRepository<BadgeEntity, Long> {
    Collection<BadgeEntity> findByApplicationEntityName(String ApplicationEntityName);
    Optional<BadgeEntity> findByApplicationEntityNameAndName(String ApplicationEntityName, String badgeName);
}
