package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.api.dto.*;
import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.entities.HistoryPointEntity;
import amt.project2.gamification.entities.UserEntity;
import amt.project2.gamification.repositories.HistoryPointRepository;
import amt.project2.gamification.repositories.UserRepository;
import amt.project2.gamification.api.UsersApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class UsersAPIController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HistoryPointRepository historyPointRepository;

    @Autowired
    HttpServletRequest req;

    public ResponseEntity<User> getUserId( @PathVariable("id") String userId) {

        ApplicationEntity targetApp = (ApplicationEntity) req.getAttribute("app");

        if (userId == null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        UserEntity userEntity = userRepository.findByApplicationEntityNameAndIdInGamifiedApplication(
                targetApp.getName(), userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toUser(userEntity));
    }

    private User toUser(UserEntity entity) {
        User user = new User();
        user.setUserId(entity.getIdInGamifiedApplication());
        user.setNbrPointOfUser(entity.getNbrPoint());
        user.setLadderOfUser(entity.getLadderSummary());
        user.setBadges(entity.getBadgesSummary());
        return user;
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
}
