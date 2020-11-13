package io.avalia.fruits.service;

import io.avalia.fruits.api.model.Event;
import io.avalia.fruits.entities.RegistrationEntity;
import io.avalia.fruits.entities.UserEntity;
import io.avalia.fruits.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventProcessor {

    @Autowired
    UserRepository userRepository;

    @Async
    @Transactional
    public void processEvent(RegistrationEntity registrationEntity, Event event) {
        UserEntity user = userRepository.findByRegistrationEntityApplicationNameAndIdInGamifiedApplication(
                registrationEntity.getApplicationName(),  event.getUserId()).orElse(null);
        if( user == null) {
            user = new UserEntity();
            user.setIdInGamifiedApplication( event.getUserId());
            user.setRegistrationEntity( registrationEntity);
            user.setNumberOfEvents( 1);
            userRepository.save( user);
        }
        else{
            user.setNumberOfEvents( user.getNumberOfEvents() + 1);
        }
    }
}
