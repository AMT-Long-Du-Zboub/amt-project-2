package io.avalia.fruits.entities;

import lombok.Data;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    private RegistrationEntity registrationEntity;

    private String idInGamifiedApplication;

    private int numberOfEvents;


}
