package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.UserBadgeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

public interface UserBadgeRepository extends CrudRepository<UserBadgeEntity, Long> {
    Collection<UserBadgeEntity> findByApplicationEntityNameAndUserEntityIdInGamifiedApplication(String applicationName, String idInGamifiedApplication);
    Collection<UserBadgeEntity> findByApplicationEntityNameAndUserEntityIdInGamifiedApplicationOrderByDateAwardedAsc(String applicationName, String idInGamifiedApplication);
    Optional<UserBadgeEntity> findByApplicationEntityNameAndUserEntityIdInGamifiedApplicationAndBadgeEntityName(String applicationName, String idInGamifiedApplication, String badgeName);
}
