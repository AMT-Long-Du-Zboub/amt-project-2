package amt.project2.gamification.repositories;

import amt.project2.gamification.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByApplicationEntityNameAndIdInGamifiedApplication(String applicationName, String targetUser);
}
