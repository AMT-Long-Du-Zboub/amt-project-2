package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.HistoryPointEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface HistoryPointRepository extends CrudRepository<HistoryPointEntity, Long>{
    Collection<HistoryPointEntity> findByApplicationEntityNameAndUserEntityIdInGamifiedApplicationOrderByWhenPointAwardedAsc(String applicationName, String idInGamifiedApp);
}
