package amt.project2.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
public class UserBadgeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private ApplicationEntity applicationEntity;

    @ManyToOne
    private BadgeEntity badgeEntity;

    private String dateAwarded;
}
