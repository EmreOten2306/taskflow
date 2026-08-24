package tech.ekya.taskflow.comment;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import tech.ekya.taskflow.common.BaseEntity;
import tech.ekya.taskflow.task.Task;
import tech.ekya.taskflow.user.AppUser;



@Getter
@Setter
@Entity
public class Comment extends BaseEntity {


    private String content;

    @ManyToOne
    private Task task;

    @ManyToOne
    private AppUser author;
}
