package amt.project2.gamification.entities;

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

    private String idInGamifiedApplication;

    private int numberOfEvents;


}
