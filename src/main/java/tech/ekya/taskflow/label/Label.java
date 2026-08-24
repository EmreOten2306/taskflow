package tech.ekya.taskflow.label;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import tech.ekya.taskflow.common.BaseEntity;
import tech.ekya.taskflow.task.Task;

import java.util.List;

@Entity
public class Label extends BaseEntity {

    @ManyToMany(mappedBy = "labels")
    private List<Task> tasks;
}
