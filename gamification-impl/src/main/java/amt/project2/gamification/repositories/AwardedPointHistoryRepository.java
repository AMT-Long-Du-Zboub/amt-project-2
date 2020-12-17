package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.AwardedPointHistoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AwardedPointHistoryRepository extends CrudRepository<AwardedPointHistoryEntity, Long>{
    Collection<AwardedPointHistoryEntity> findByApplicationEntityNameAndUserEntityIdInGamifiedApplicationOrderByWhenPointAwardedAsc(String applicationName, String idInGamifiedApp);
}
