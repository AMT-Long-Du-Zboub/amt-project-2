package amt.project2.gamification.service;

import amt.project2.gamification.api.dto.Event;
import amt.project2.gamification.repositories.UserRepository;
import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.entities.UserEntity;
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
    public void processEvent(ApplicationEntity applicationEntity, Event event) {
        UserEntity user = userRepository.findByApplicationEntityNameAndIdInGamifiedApplication(
                applicationEntity.getApiKey(),  event.getUserId()).orElse(null);
        if( user == null) {
            user = new UserEntity();
            user.setIdInGamifiedApplication( event.getUserId());
            user.setApplicationEntity(applicationEntity);
            user.setNumberOfEvents( 1);
            userRepository.save( user);
        }
        else{
            user.setNumberOfEvents( user.getNumberOfEvents() + 1);
        }
    }
}
