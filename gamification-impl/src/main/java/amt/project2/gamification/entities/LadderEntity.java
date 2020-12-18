package amt.project2.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"application_entity_id", "level"})
})
public class LadderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApplicationEntity applicationEntity;

    private int level;

    private String title;

    private int nbrPoint;

}
