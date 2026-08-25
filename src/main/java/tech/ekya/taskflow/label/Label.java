package tech.ekya.taskflow.label;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import tech.ekya.taskflow.common.BaseEntity;
import tech.ekya.taskflow.task.Task;
import java.util.List;
@Setter
@Getter
@Entity
public class Label extends BaseEntity {

    @ManyToMany(mappedBy = "labels")
    private List<Task> tasks;

    private String name;
}
