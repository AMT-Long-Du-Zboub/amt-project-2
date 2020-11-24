package amt.project2.gamification.service;

import amt.project2.gamification.api.dto.Event;
import amt.project2.gamification.api.dto.Rule;
import amt.project2.gamification.api.dto.User;
import amt.project2.gamification.entities.*;
import amt.project2.gamification.repositories.BadgeRepository;
import amt.project2.gamification.repositories.LadderRepository;
import amt.project2.gamification.repositories.RuleRepository;
import amt.project2.gamification.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventProcessor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    LadderRepository ladderRepository;

    @Autowired
    BadgeRepository badgeRepository;

    @Async
    @Transactional
    public void processEvent(ApplicationEntity applicationEntity, Event event) {
        UserEntity user = userRepository.findByApplicationEntityNameAndIdInGamifiedApplication(
                applicationEntity.getName(),  event.getUserId()).orElse(null);

        if( user == null) {
            user = new UserEntity();
            user.setIdInGamifiedApplication(event.getUserId());
            user.setApplicationEntity(applicationEntity);
            user.setNbrPoint(0);
            userRepository.save(user);
        }

        RuleEntity ruleEntity = ruleRepository.findByApplicationEntityNameAndType(applicationEntity.getName(), event.getType()).orElse(null);

        if(ruleEntity != null){
            processRule(ruleEntity, user, applicationEntity.getName());
        }
    }

    private void processRule(RuleEntity ruleEntity, UserEntity user, String applicationName){
        if (ruleEntity.getAwardPoint() != 0){

            user.addPoint(ruleEntity.getAwardPoint());
            LadderEntity ladderEntity = ladderRepository.findByApplicationEntityNameAndLevel(applicationName, user.getActualLadder().getLevel() + 1).orElse(null);

            if (ladderEntity != null && user.getNbrPoint() >= ladderEntity.getNbrPoint()){
                user.setActualLadder(ladderEntity);
            }
        }

        if (ruleEntity.getAwardBadge() != null && !ruleEntity.getAwardBadge().isEmpty()){
            BadgeEntity badgeEntity = badgeRepository.findByApplicationEntityNameAndName(applicationName, ruleEntity.getAwardBadge()).orElse(null);
            if (badgeEntity != null){
                user.addBadge(badgeEntity);
            }
        }
        userRepository.save(user);
    }
}
