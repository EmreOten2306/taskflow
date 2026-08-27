package tech.ekya.taskflow.task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.task.taskenums.TaskPriority;
import tech.ekya.taskflow.task.taskenums.TaskStatus;

import java.time.LocalDateTime;


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
        return taskService.assigneeLabelToTask(id, labelId);
    }

    @GetMapping ("/projects/{projectId}/tasks")
    public Page<Task> getProjectTasks(@PathVariable Long projectId,
                                  Pageable pageable){
        return taskService.getProjectTasks(projectId, pageable);
    }
    @GetMapping("/tasks")
    public Page<Task> getTasks(Pageable pageable,
                               @RequestParam(required = false) TaskStatus status,
                               @RequestParam(required = false) TaskPriority priority,
                               @RequestParam(required = false) Long assigneeId,
                               @RequestParam(required = false) LocalDateTime dueBefore,
                               @RequestParam(required = false) String search){
        return taskService.getAllTasks(pageable,
                status,
                priority,
                assigneeId,
                dueBefore,
                search);
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
        taskService.removeAssigneeLabelToTask(id, labelId);
    }

    @DeleteMapping ("/tasks/{id}")
    public void deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }

}
