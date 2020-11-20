package amt.project2.gamification.api.endpoints;

import amt.project2.gamification.api.dto.Badge;
import amt.project2.gamification.api.dto.BadgeSummary;
import amt.project2.gamification.api.BadgesApi;
import amt.project2.gamification.entities.ApplicationEntity;
import amt.project2.gamification.entities.BadgeEntity;
import amt.project2.gamification.repositories.ApplicationRepository;
import amt.project2.gamification.repositories.BadgeRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BadgesAPIController implements BadgesApi {
    @Autowired
    BadgeRepository badgeRepository;

    @Autowired
    ApplicationRepository applicationRepository;


    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity addBadge(@RequestHeader(value = "x-gamification-token") String xGamificationToken, @ApiParam(value = "", required = true) @Valid @RequestBody Badge badge) {
        ApplicationEntity targetApp = applicationRepository.findByApiKey(xGamificationToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        BadgeEntity newBadgeEntity = toBadgeEntity(targetApp, badge);

        try {
            badgeRepository.save(newBadgeEntity);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
    }

    public ResponseEntity<List<BadgeSummary>> getBadges(@RequestHeader(value = "x-gamification-token") String xGamificationToken) {
        List<BadgeSummary> badges = new ArrayList<>();
        ApplicationEntity targetApp = applicationRepository.findByApiKey(xGamificationToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        for (BadgeEntity badgeEntity : badgeRepository.findByApplicationEntityName(targetApp.getName())) {
            badges.add(toBadge(badgeEntity));
        }
        return ResponseEntity.ok(badges);
    }

    private BadgeEntity toBadgeEntity(ApplicationEntity targetApp, Badge badge) {
        BadgeEntity entity = new BadgeEntity();
        entity.setName(badge.getName());
        entity.setApplicationEntity(targetApp);
        entity.setDescription(badge.getDescription());
        return entity;
    }

    private BadgeSummary toBadge(BadgeEntity entity) {
        BadgeSummary badge = new BadgeSummary();
        badge.name(entity.getName());
        return badge;
    }

}
