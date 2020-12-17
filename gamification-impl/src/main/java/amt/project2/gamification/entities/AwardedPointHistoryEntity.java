package amt.project2.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class AwardedPointHistoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private ApplicationEntity applicationEntity;

    private int pointAwarded;

    private int totalOfPointAfterAwarded;

    private String whenPointAwarded;
}
