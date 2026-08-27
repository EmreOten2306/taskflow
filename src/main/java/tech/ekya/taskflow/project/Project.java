package tech.ekya.taskflow.project;
import jakarta.persistence.*;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser owner;

    @Column(unique = true)
    private String code;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;

}
