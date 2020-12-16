package amt.project2.gamification.entities;

import amt.project2.gamification.api.dto.LadderSummary;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

    private String idInGamifiedApplication;

    private int nbrPoint;

    public void addPoint(int number){
        nbrPoint += number;
    }

    public LadderSummary getLadderSummary(){
        LadderSummary ladderSummary = new LadderSummary();
        ladderSummary.setLevel(actualLadder.getLevel());
        ladderSummary.setNbrPoint(actualLadder.getNbrPoint());
        ladderSummary.setTitle(actualLadder.getTitle());

        return ladderSummary;
    }
}
