package tech.ekya.taskflow.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import tech.ekya.taskflow.common.BaseEntity;

@Setter
@Getter
@Entity
public class AppUser extends BaseEntity {

    private String fullName;

    private String passwordHash;

    @Column (unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AppUser (){

    }


}
