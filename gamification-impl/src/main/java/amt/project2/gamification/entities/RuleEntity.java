package amt.project2.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data

@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"application_entity_id", "type"})
})

public class RuleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private ApplicationEntity applicationEntity;

    private String type;

    private String awardBadge;

    private int awardPoint;
}
