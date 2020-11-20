package amt.project2.gamification.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Constraint;
import java.io.Serializable;

@Entity
@Data
public class ApplicationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true)
    private String name;

    private String apiKey;
}
