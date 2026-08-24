package tech.ekya.taskflow.task;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.task.taskenums.TaskStatus;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    private  final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping ("/projects/{projectId}/tasks")
    public Task createTask(@RequestBody Task task,
                           @PathVariable Long projectId){
        return taskService.createTask(projectId, task);
    }
    @PostMapping("/tasks/{id}/labels/{labelId}")
    public Task createTaskLabel(@PathVariable Long id,
                                @PathVariable Long labelId){
        return taskService.createLabelToTask(id, labelId);
    }


    @GetMapping ("/projects/{projectId}/tasks")
    public List<Task> getAllTasks(@PathVariable Long projectId){
        return taskService.getAllTasks(projectId);
    }
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable Long id,
                           @RequestBody Task task){
    return taskService.updateTaskById(id,task);
    }

    @PatchMapping("/tasks/{id}/status")
    public Task updateTaskStatus(@PathVariable Long id,
                                       @RequestBody TaskStatus taskStatus){
        return taskService.updateTaskStatus(id,taskStatus);
    }

    @PatchMapping("/tasks/{id}/assignee")
    public Task updateTaskAssignee(@PathVariable Long id,
                                   @RequestBody Long assigneeId ){
        return taskService.updateTaskAssignee(id,assigneeId);
    }
    @DeleteMapping("/tasks/{id}/labels/{labelId}")
    public void removeLabelToTask(@PathVariable Long id,@PathVariable Long labelId){
        taskService.removeLabelToTask(id, labelId);
    }

    @DeleteMapping ("/tasks/{id}")
    public void deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }

}
