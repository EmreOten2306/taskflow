package tech.ekya.taskflow.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Tasks",
        description = "Task management operations"
)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /// CREATE TASK
    @Operation(
            summary = "Create a task",
            description = "Creates a new task for a project"
    )
    @ApiResponse(responseCode = "200", description = "Task created successfully")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @PostMapping("/projects/{id}/tasks")
    public TaskResponse createTask(
            @Valid @RequestBody CreateTaskRequest request,
            @PathVariable Long id) {

        return taskService.createTask(id, request);
    }


    /// GET PROJECT'S TASKS
    @Operation(
            summary = "Get project tasks",
            description = "Returns paginated tasks belonging to a project"
    )
    @ApiResponse(responseCode = "200", description = "Project tasks retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @GetMapping("/projects/{id}/tasks")
    public Page<TaskResponse> getProjectTasks(
            @PathVariable Long id,
            @ParameterObject Pageable pageable) {

        return taskService.getProjectTasks(id, pageable);
    }


    /// GET ALL TASKS
    @Operation(
            summary = "Get all tasks",
            description = "Returns paginated tasks with optional filtering and search"
    )
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully")
    @GetMapping("/tasks")
    public Page<TaskResponse> getTasks(
            @ParameterObject Pageable pageable,
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
    @Operation(
            summary = "Get task by ID",
            description = "Returns a task by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @GetMapping("/tasks/{id}")
    public TaskResponse getTaskById(
            @PathVariable Long id) {

        return taskService.getTaskById(id);
    }


    /// UPDATE TASK
    @Operation(
            summary = "Update a task",
            description = "Updates an existing task by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Task updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @PutMapping("/tasks/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request) {

        return taskService.updateTaskById(id, request);
    }


    /// UPDATE TASK STATUS
    @Operation(
            summary = "Update task status",
            description = "Updates the status of an existing task"
    )
    @ApiResponse(responseCode = "200", description = "Task status updated successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @PatchMapping("/tasks/{id}/status")
    public TaskResponse updateTaskStatus(
            @PathVariable Long id,
            @RequestBody TaskStatus taskStatus) {

        return taskService.updateTaskStatus(id, taskStatus);
    }


    /// UPDATE TASK ASSIGNEE
    @Operation(
            summary = "Update task assignee",
            description = "Changes the user assigned to a task"
    )
    @ApiResponse(responseCode = "200", description = "Task assignee updated successfully")
    @ApiResponse(responseCode = "404", description = "Task or user not found")
    @PatchMapping("/tasks/{id}/assignee")
    public TaskResponse updateTaskAssignee(
            @PathVariable Long id,
            @RequestBody Long assigneeId) {

        return taskService.updateTaskAssignee(id, assigneeId);
    }


    /// ADD LABEL TO TASK
    @Operation(
            summary = "Add label to task",
            description = "Assigns a label to a task"
    )
    @ApiResponse(responseCode = "200", description = "Label added to task successfully")
    @ApiResponse(responseCode = "404", description = "Task or label not found")
    @PostMapping("/tasks/{id}/labels/{labelId}")
    public TaskResponse createTaskLabel(
            @PathVariable Long id,
            @PathVariable Long labelId) {

        return taskService.assigneeLabelToTask(id, labelId);
    }


    /// REMOVE LABEL FROM TASK
    @Operation(
            summary = "Remove label from task",
            description = "Removes a label from a task"
    )
    @ApiResponse(responseCode = "200", description = "Label removed from task successfully")
    @ApiResponse(responseCode = "404", description = "Task or label not found")
    @DeleteMapping("/tasks/{id}/labels/{labelId}")
    public void removeLabelToTask(
            @PathVariable Long id,
            @PathVariable Long labelId) {

        taskService.removeAssigneeLabelToTask(id, labelId);
    }


    /// DELETE TASK
    @Operation(
            summary = "Delete a task",
            description = "Deletes a task by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Task deleted successfully")
    @ApiResponse(responseCode = "404", description = "Task not found")
    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById(
            @PathVariable Long id) {

        taskService.deleteTaskById(id);
    }
}
