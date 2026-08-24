package tech.ekya.taskflow.task;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import tech.ekya.taskflow.common.BaseEntity;
import tech.ekya.taskflow.project.Project;
import tech.ekya.taskflow.user.AppUser;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Task extends BaseEntity {

private String title;
private String description;

    @Enumerated(EnumType.STRING)
private TaskStatus status;
    @Enumerated(EnumType.STRING)
private TaskPriority priority;

private LocalDateTime dueDate;

@ManyToOne(optional = false)
private Project project;

@ManyToOne
private AppUser assignee;
}
