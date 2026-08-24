package tech.ekya.taskflow.task;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public void updateTaskEntity(Task newTask, Task existingTask) {
        existingTask.setTitle(newTask.getTitle());
        existingTask.setDescription(newTask.getDescription());
        existingTask.setStatus(newTask.getStatus());
        existingTask.setPriority(newTask.getPriority());
        existingTask.setDueDate(newTask.getDueDate());
    }
}
