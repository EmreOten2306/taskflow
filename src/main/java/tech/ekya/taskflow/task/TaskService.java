package tech.ekya.taskflow.task;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.DuplicateResourceException;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.exception.UnprocessableEntityException;
import tech.ekya.taskflow.label.Label;
import tech.ekya.taskflow.label.LabelRepository;
import tech.ekya.taskflow.project.Project;
import tech.ekya.taskflow.project.ProjectRepository;
import tech.ekya.taskflow.project.ProjectStatus;
import tech.ekya.taskflow.task.dto.CreateTaskRequest;
import tech.ekya.taskflow.task.dto.TaskResponse;
import tech.ekya.taskflow.task.dto.UpdateTaskRequest;
import tech.ekya.taskflow.task.taskenums.TaskPriority;
import tech.ekya.taskflow.task.taskenums.TaskStatus;
import tech.ekya.taskflow.user.AppUser;
import tech.ekya.taskflow.user.AppUserRepository;

import java.time.LocalDateTime;

@Transactional
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final AppUserRepository appUserRepository;
    private final LabelRepository labelRepository;

    public TaskService(
            TaskRepository taskRepository,
            ProjectRepository projectRepository,
            TaskMapper taskMapper,
            AppUserRepository appUserRepository,
            LabelRepository labelRepository) {

        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
        this.appUserRepository = appUserRepository;
        this.labelRepository = labelRepository;
    }

    /// CREATE TASK
    public TaskResponse createTask(
            Long projectId,
            CreateTaskRequest request) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + projectId
                ));

        if (project.getStatus() == ProjectStatus.ARCHIVED) {
            throw new UnprocessableEntityException(
                    "Task cannot be created in an archived project"
            );
        }

        if (request.priority() == TaskPriority.CRITICAL
                && request.dueDate() == null) {

            throw new UnprocessableEntityException(
                    "critical task due date should not be null"
            );
        }

        if (taskRepository.existsByProjectIdAndTitle(
                projectId,
                request.title())) {

            throw new DuplicateResourceException(
                    "A task with the same title already exists in this project"
            );
        }

        Task task = taskMapper.toEntity(request);

        if (request.assigneeId() != null) {

            AppUser assignee = appUserRepository.findById(request.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Assignee not found with id: " + request.assigneeId()
                    ));

            task.setAssignee(assignee);
        }

        task.setProject(project);

        Task savedTask = taskRepository.save(task);

        return taskMapper.toResponse(savedTask);
    }


    /// GET PROJECT'S TASK
    public Page<TaskResponse> getProjectTasks(
            Long projectId,
            Pageable pageable) {

        projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + projectId
                ));

        return taskRepository.findByProjectId(projectId, pageable)
                .map(taskMapper::toResponse);
    }


    /// GET ALL TASKS
    public Page<TaskResponse> getAllTasks(
            Pageable pageable,
            TaskStatus status,
            TaskPriority priority,
            Long assigneeId,
            LocalDateTime dueBefore,
            String search) {

        Specification<Task> spec = Specification.unrestricted();

        if (status != null) {
            spec = spec.and(TaskSpecification.hasStatus(status));
        }

        if (priority != null) {
            spec = spec.and(TaskSpecification.hasPriority(priority));
        }

        if (assigneeId != null) {
            spec = spec.and(TaskSpecification.hasAssigneeId(assigneeId));
        }

        if (dueBefore != null) {
            spec = spec.and(TaskSpecification.dueBefore(dueBefore));
        }

        if (search != null && !search.isBlank()) {
            spec = spec.and(TaskSpecification.search(search));
        }

        return taskRepository.findAll(spec, pageable)
                .map(taskMapper::toResponse);
    }


    /// GET TASK BY ID
    public TaskResponse getTaskById(Long taskId) {

        Task task = taskRepository.findTaskWithDetailsById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        return taskMapper.toResponse(task);
    }


    /// UPDATE TASK BY ID
    public TaskResponse updateTaskById(
            Long taskId,
            UpdateTaskRequest request) {

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        if (taskRepository.existsByProjectIdAndTitleAndIdNot(
                existingTask.getProject().getId(),
                request.title(),
                taskId)) {

            throw new DuplicateResourceException(
                    "A task with the same title already exists in this project"
            );
        }

        taskMapper.updateTaskEntity(request, existingTask);

        Task savedTask = taskRepository.save(existingTask);

        return taskMapper.toResponse(savedTask);
    }


    /// UPDATE TASK STATUS
    public TaskResponse updateTaskStatus(
            Long taskId,
            TaskStatus status) {

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        existingTask.setStatus(status);

        if (status == TaskStatus.DONE) {
            existingTask.setCompletedAt(LocalDateTime.now());
        } else {
            existingTask.setCompletedAt(null);
        }

        Task savedTask = taskRepository.save(existingTask);

        return taskMapper.toResponse(savedTask);
    }


    /// UPDATE TASK ASSIGNEE
    public TaskResponse updateTaskAssignee(
            Long taskId,
            Long assigneeId) {

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        AppUser assignee = appUserRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + assigneeId
                ));

        existingTask.setAssignee(assignee);

        Task savedTask = taskRepository.save(existingTask);

        return taskMapper.toResponse(savedTask);
    }


    /// ADD LABEL TO TASK
    public TaskResponse assigneeLabelToTask(
            Long taskId,
            Long labelId) {

        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        Label existingLabel = labelRepository.findById(labelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Label not found with id: " + labelId
                ));

        existingTask.getLabels().add(existingLabel);

        Task savedTask = taskRepository.save(existingTask);

        return taskMapper.toResponse(savedTask);
    }


    /// REMOVE LABEL FROM TASK
    public void removeAssigneeLabelToTask(
            Long taskId,
            Long labelId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        Label label = labelRepository.findById(labelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Label not found with id: " + labelId
                ));

        task.getLabels().remove(label);

        taskRepository.save(task);
    }


    /// DELETE TASK
    public void deleteTaskById(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));

        taskRepository.delete(task);
    }
}