package amt.project2.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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
    private Set<BadgeEntity> badges;

    private String idInGamifiedApplication;

    private int numberOfEvents;

    private int nbrPoint;

    public void addPoint(int number){
        nbrPoint += number;
    }

    public void addBadge(BadgeEntity badgeEntity){
        badges.add(badgeEntity);
    }
}
