package amt.project2.gamification.service;

import amt.project2.gamification.api.dto.Event;
import amt.project2.gamification.entities.*;
import amt.project2.gamification.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Autowired
    HistoryPointRepository historyPointRepository;

    @Autowired
    UserBadgeRepository userBadgeRepository;

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
            LadderEntity defaultLadder = ladderRepository.findByApplicationEntityNameAndLevel(applicationEntity.getName(), 0).orElseThrow();
            user.setActualLadder(defaultLadder);
            userRepository.save(user);
        }

        RuleEntity ruleEntity = ruleRepository.findByApplicationEntityNameAndType(applicationEntity.getName(), event.getType()).orElse(null);

        if(ruleEntity != null){
            processRule(ruleEntity, user, applicationEntity);
        }
    }

    private void processRule(RuleEntity ruleEntity, UserEntity user, ApplicationEntity applicationEntity){
        if (ruleEntity.getAwardPoint() != 0){

            user.addPoint(ruleEntity.getAwardPoint());

            LadderEntity ladderEntity = ladderRepository.findByApplicationEntityNameAndLevel(applicationEntity.getName(), user.getActualLadder().getLevel() + 1).orElse(null);
            if (ladderEntity != null && user.getNbrPoint() >= ladderEntity.getNbrPoint()){
                user.setActualLadder(ladderEntity);
            }
            addInHistoryPoint(user, applicationEntity, ruleEntity.getAwardPoint(), user.getNbrPoint());
        }

        if (ruleEntity.getAwardBadge() != null && !ruleEntity.getAwardBadge().isEmpty()){
            BadgeEntity badgeEntity = badgeRepository.findByApplicationEntityNameAndName(applicationEntity.getName(), ruleEntity.getAwardBadge()).orElse(null);

            if (badgeEntity != null){
                newUserBadge(badgeEntity, user, applicationEntity);
            }
        }
        userRepository.save(user);
    }

    private void addInHistoryPoint(UserEntity user, ApplicationEntity applicationEntity, int nbrPoint, int TotalAfterAdd){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        HistoryPointEntity historyPointEntity = new HistoryPointEntity();
        historyPointEntity.setApplicationEntity(applicationEntity);
        historyPointEntity.setUserEntity(user);
        historyPointEntity.setPointAwarded(nbrPoint);
        historyPointEntity.setTotalOfPointAfterAwarded(TotalAfterAdd);
        historyPointEntity.setWhenPointAwarded(formatter.format(date));
        historyPointRepository.save(historyPointEntity);
    }

    private void newUserBadge(BadgeEntity badgeEntity, UserEntity userEntity, ApplicationEntity applicationEntity){
        UserBadgeEntity userBadgeEntity = new UserBadgeEntity();

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        userBadgeEntity.setBadgeEntity(badgeEntity);
        userBadgeEntity.setApplicationEntity(applicationEntity);
        userBadgeEntity.setUserEntity(userEntity);
        userBadgeEntity.setDateAwarded(formatter.format(date));
        userBadgeRepository.save(userBadgeEntity);
    }
}
