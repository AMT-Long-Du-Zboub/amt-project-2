package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.api.dto.*;
import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.entities.HistoryPointEntity;
import amt.project2.gamification.entities.UserBadgeEntity;
import amt.project2.gamification.entities.UserEntity;
import amt.project2.gamification.repositories.HistoryPointRepository;
import amt.project2.gamification.repositories.UserBadgeRepository;
import amt.project2.gamification.repositories.UserRepository;
import amt.project2.gamification.api.UsersApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class UsersAPIController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryPointRepository historyPointRepository;

    @Autowired
    HttpServletRequest req;

    @Autowired
    UserBadgeRepository userBadgeRepository;

    public ResponseEntity<User> getUserId( @PathVariable("id") String userId) {

        ApplicationEntity targetApp = (ApplicationEntity) req.getAttribute("app");

        if (userId == null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        UserEntity userEntity = userRepository.findByApplicationEntityNameAndIdInGamifiedApplication(
                targetApp.getName(), userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toUser(userEntity, targetApp));
    }

    private User toUser(UserEntity entity, ApplicationEntity app) {
        User user = new User();
        user.setUserId(entity.getIdInGamifiedApplication());
        user.setNbrPointOfUser(entity.getNbrPoint());
        user.setLadderOfUser(entity.getLadderSummary());
        user.setBadges(provideBadgeSummary(entity.getIdInGamifiedApplication(),app.getName()));
        return user;
    }

    public List<BadgeSummary> provideBadgeSummary(String userIdGamified, String appName){
        List<BadgeSummary> badgeSummaries = new ArrayList<>();

        Collection<UserBadgeEntity> userBadgeEntities = userBadgeRepository.
                findByApplicationEntityNameAndUserEntityIdInGamifiedApplication(appName, userIdGamified);

        for (UserBadgeEntity userBadgeEntity : userBadgeEntities) {
            BadgeSummary badgeSummary = new BadgeSummary();
            badgeSummary.setName(userBadgeEntity.getBadgeEntity().getName());
            badgeSummary.setDescription(userBadgeEntity.getBadgeEntity().getDescription());
            badgeSummaries.add(badgeSummary);
        }
        return badgeSummaries;

    }

    public ResponseEntity<TopTenByPoint> top10ByPoint(){
        ApplicationEntity targetApp = (ApplicationEntity) req.getAttribute("app");

        Collection<UserEntity> users = userRepository.findTop10ByApplicationEntityNameOrderByNbrPointDesc(targetApp.getName());

        TopTenByPoint top10User = provideTop10(users);

        return ResponseEntity.ok(top10User);
    }

    private TopTenByPoint provideTop10(Collection<UserEntity> users){
        TopTenByPoint topTenByPoint = new TopTenByPoint();

        for (UserEntity user: users) {
            UserPoint userPoint = new UserPoint();
            userPoint.setUserId(user.getIdInGamifiedApplication());
            userPoint.setNbrPoint(user.getNbrPoint());
            userPoint.setLevel(user.getLadderSummary().getLevel());
            topTenByPoint.addListsItem(userPoint);
        }

        return topTenByPoint;
    }

    public ResponseEntity<HistoryOfPointForAnUser> getHistoryByUserId(@PathVariable("id") String userId){
        ApplicationEntity targetApp = (ApplicationEntity) req.getAttribute("app");

        if (userId == null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        Collection<HistoryPointEntity> historyOfUser = historyPointRepository
                .findByApplicationEntityNameAndUserEntityIdInGamifiedApplicationOrderByWhenPointAwardedAsc(targetApp.getName(), userId);

        HistoryOfPointForAnUser history = provideHistoryOfPoint(historyOfUser);
        return ResponseEntity.ok(history);
    }

    public HistoryOfPointForAnUser provideHistoryOfPoint(Collection<HistoryPointEntity> historyOfUser){
        HistoryOfPointForAnUser history = new HistoryOfPointForAnUser();
        for (HistoryPointEntity entity: historyOfUser) {
            HistoryOfPoint historyOfPoint = new HistoryOfPoint();

            historyOfPoint.setDate(entity.getWhenPointAwarded());
            historyOfPoint.setPointAwarded(entity.getPointAwarded());
            historyOfPoint.setTotalAfter(entity.getTotalOfPointAfterAwarded());

            history.addHistoryItem(historyOfPoint);
        }
        return history;
    }

    public ResponseEntity<BadgeAwardedHistoryForAnUser> getBadgeAwardedHistory(@PathVariable("id") String userId){
        ApplicationEntity targetApp = (ApplicationEntity) req.getAttribute("app");

        if (userId == null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }


        Collection<UserBadgeEntity> userBadgeEntity = userBadgeRepository
                .findByApplicationEntityNameAndUserEntityIdInGamifiedApplicationOrderByDateAwardedAsc(targetApp.getName(), userId);

        BadgeAwardedHistoryForAnUser history = provideBadgeAwardedHistory(userBadgeEntity);


        return ResponseEntity.ok(history);
    }

    private BadgeAwardedHistoryForAnUser provideBadgeAwardedHistory(Collection<UserBadgeEntity> userBadgeEntities){
        BadgeAwardedHistoryForAnUser history = new BadgeAwardedHistoryForAnUser();

        for (UserBadgeEntity userBadgeEntity:userBadgeEntities) {
            BadgeAwardedHistory badge = new BadgeAwardedHistory();
            badge.setName(userBadgeEntity.getBadgeEntity().getName());
            badge.setDescription(userBadgeEntity.getBadgeEntity().getDescription());
            badge.setDate(userBadgeEntity.getDateAwarded());

            history.addHistoryItem(badge);
        }
        return history;
    }
}
