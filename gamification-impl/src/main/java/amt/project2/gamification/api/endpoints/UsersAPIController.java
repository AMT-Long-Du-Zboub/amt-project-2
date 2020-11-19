package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.entities.UserEntity;
import amt.project2.gamification.repositories.ApplicationRepository;
import amt.project2.gamification.repositories.UserRepository;
import amt.project2.gamification.api.UsersApi;
import amt.project2.gamification.api.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class UsersAPIController implements UsersApi {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    public ResponseEntity<User> getUserId(@RequestHeader(value = "x-gamification-token") String xGamificationToken, @PathVariable("id") String userId) {
        String targetAppName = applicationRepository.findByApiKey(xGamificationToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getName();
        if (userId == null){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }

        UserEntity userEntity = userRepository.findByApplicationEntityNameAndIdInGamifiedApplication(
                targetAppName, userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok(toUser(userEntity));
    }

    private User toUser(UserEntity entity) {
        User user = new User();
        user.setUserId(entity.getIdInGamifiedApplication());
        user.setNumberOfEvents(entity.getNumberOfEvents());
        return user;
    }
}
