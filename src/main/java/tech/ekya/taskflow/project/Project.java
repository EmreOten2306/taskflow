package tech.ekya.taskflow.project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tech.ekya.taskflow.common.BaseEntity;
import tech.ekya.taskflow.user.AppUser;
@Setter
@Getter
@Entity
public class Project extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JsonIgnore
    private AppUser owner;

    @Column(unique = true)
    private String code;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

}
