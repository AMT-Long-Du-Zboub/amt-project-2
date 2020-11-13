package io.avalia.fruits.repositories;

import io.avalia.fruits.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByRegistrationEntityApplicationNameAndIdInGamifiedApplication(String registrationEntityName, String targetUser);
}
