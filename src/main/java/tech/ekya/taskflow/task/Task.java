package tech.ekya.taskflow.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tech.ekya.taskflow.comment.Comment;
import tech.ekya.taskflow.common.BaseEntity;
import tech.ekya.taskflow.label.Label;
import tech.ekya.taskflow.project.Project;
import tech.ekya.taskflow.user.AppUser;
import java.time.LocalDateTime;
import java.util.List;

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

@OneToMany(mappedBy = "task" , cascade = CascadeType.ALL)
private List<Comment> comments;

@ManyToOne(optional = false)
private Project project;

@ManyToOne
private AppUser assignee;

@ManyToMany
@JoinTable(name = "task_label")
private List<Label> labels;

}
