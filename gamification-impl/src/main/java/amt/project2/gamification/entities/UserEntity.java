package amt.project2.gamification.entities;

import amt.project2.gamification.api.dto.BadgeSummary;
import amt.project2.gamification.api.dto.LadderSummary;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApplicationEntity applicationEntity;

    @ManyToOne
    private LadderEntity actualLadder;

    @ManyToMany
    private Set<BadgeEntity> badges = new HashSet<>();

    @OneToMany
    private Set<HistoryPointEntity> historyPointEntity;

    private String idInGamifiedApplication;

    private int nbrPoint;

    public void addPoint(int number){
        nbrPoint += number;
    }

    public void addBadge(BadgeEntity badgeEntity){
        badges.add(badgeEntity);
    }

    public List<BadgeSummary> getBadgesSummary(){
        List<BadgeSummary> badgesList = new ArrayList<>();

        for ( BadgeEntity badge : badges) {
            BadgeSummary badgeSummary = new BadgeSummary();
            badgeSummary.setName(badge.getName());
            badgesList.add(badgeSummary);
        }

        return badgesList;
    }

    public LadderSummary getLadderSummary(){
        LadderSummary ladderSummary = new LadderSummary();
        ladderSummary.setLevel(actualLadder.getLevel());
        ladderSummary.setNbrPoint(actualLadder.getNbrPoint());
        ladderSummary.setTitle(actualLadder.getTitle());

        return ladderSummary;
    }
}
