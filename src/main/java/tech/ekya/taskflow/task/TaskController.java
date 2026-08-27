package tech.ekya.taskflow.task;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import tech.ekya.taskflow.task.dto.CreateTaskRequest;
import tech.ekya.taskflow.task.dto.TaskResponse;
import tech.ekya.taskflow.task.dto.UpdateTaskRequest;
import tech.ekya.taskflow.task.taskenums.TaskPriority;
import tech.ekya.taskflow.task.taskenums.TaskStatus;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    /// CREATE TASK
    @PostMapping("/projects/{id}/tasks")
    public TaskResponse createTask(
            @Valid @RequestBody CreateTaskRequest request,
            @PathVariable Long id) {

        return taskService.createTask(id, request);
    }


    /// GET PROJECT'S TASKS
    @GetMapping("/projects/{id}/tasks")
    public Page<TaskResponse> getProjectTasks(
            @PathVariable Long id,
            @ParameterObject Pageable pageable) {

        return taskService.getProjectTasks(id, pageable);
    }


    /// GET ALL TASKS
    @GetMapping("/tasks")
    public Page<TaskResponse> getTasks(
            @ParameterObject  Pageable pageable,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(required = false) LocalDateTime dueBefore,
            @RequestParam(required = false) String search) {

        return taskService.getAllTasks(
                pageable,
                status,
                priority,
                assigneeId,
                dueBefore,
                search
        );
    }


    /// GET TASK BY ID
    @GetMapping("/tasks/{id}")
    public TaskResponse getTaskById(
            @PathVariable Long id) {

        return taskService.getTaskById(id);
    }


    /// UPDATE TASK
    @PutMapping("/tasks/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {

        return taskService.updateTaskById(id, request);
    }


    /// UPDATE TASK STATUS
    @PatchMapping("/tasks/{id}/status")
    public TaskResponse updateTaskStatus(
            @PathVariable Long id,
            @RequestBody TaskStatus taskStatus) {

        return taskService.updateTaskStatus(id, taskStatus);
    }


    /// UPDATE TASK ASSIGNEE
    @PatchMapping("/tasks/{id}/assignee")
    public TaskResponse updateTaskAssignee(
            @PathVariable Long id,
            @RequestBody Long assigneeId) {

        return taskService.updateTaskAssignee(id, assigneeId);
    }


    /// ADD LABEL TO TASK
    @PostMapping("/tasks/{id}/labels/{labelId}")
    public TaskResponse createTaskLabel(
            @PathVariable Long id,
            @PathVariable Long labelId) {

        return taskService.assigneeLabelToTask(id, labelId);
    }


    /// REMOVE LABEL FROM TASK
    @DeleteMapping("/tasks/{id}/labels/{labelId}")
    public void removeLabelToTask(
            @PathVariable Long id,
            @PathVariable Long labelId) {

        taskService.removeAssigneeLabelToTask(id, labelId);
    }


    /// DELETE TASK
    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById(
            @PathVariable Long id) {

        taskService.deleteTaskById(id);
    }
}