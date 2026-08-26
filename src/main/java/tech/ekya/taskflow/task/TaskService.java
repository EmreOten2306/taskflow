package tech.ekya.taskflow.task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.label.Label;
import tech.ekya.taskflow.label.LabelRepository;
import tech.ekya.taskflow.project.Project;
import tech.ekya.taskflow.project.ProjectRepository;
import tech.ekya.taskflow.task.taskenums.TaskPriority;
import tech.ekya.taskflow.task.taskenums.TaskStatus;
import tech.ekya.taskflow.user.AppUser;
import tech.ekya.taskflow.user.AppUserRepository;

import java.time.LocalDateTime;


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final AppUserRepository appUserRepository;
    private final LabelRepository labelRepository;

    public TaskService(TaskRepository taskRepository
            , ProjectRepository projectRepository
            , TaskMapper taskMapper
            , AppUserRepository appUserRepository
            , LabelRepository labelRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
        this.appUserRepository = appUserRepository;
        this.labelRepository = labelRepository;
    }

    /// CREATE TASK
    public Task createTask(Long projectId, Task task) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + projectId
                ));

        task.setProject(project);
        return taskRepository.save(task);
    }

    /// GET PROJECT'S TASK
    public Page<Task> getProjectTasks(Long projectId ,
                                  Pageable pageable) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + projectId
                ));

        return taskRepository.findByProjectId(projectId , pageable);
    }

    ///GET ALL TASKS
    public Page<Task> getAllTasks(Pageable pageable,
                                  TaskStatus status,
                                  TaskPriority priority,
                                  Long assigneeId,
                                  LocalDateTime dueBefore,
                                  String search) {
        if (status != null) {
            return taskRepository.findByStatus(status, pageable);
        }
        if (priority != null) {
            return taskRepository.findByPriority(priority, pageable);
        }
        if (assigneeId != null) {
            return taskRepository.findByAssigneeId(assigneeId, pageable);
        }
        if (dueBefore != null){
            return taskRepository.findByDueDateBefore(dueBefore, pageable);
        }
        if (search != null && !search.isBlank()) {
            return taskRepository.findByTitleContainingOrDescriptionContaining(
                    search,
                    search,
                    pageable
            );
        }
        return taskRepository.findAll(pageable);

    }



    /// GET TASK BY ID
    public Task getTaskById(Long taskId) {
         Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));
        return task;
    }

    /// UPDATE TASK
    public Task updateTaskById(Long taskId, Task task) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));
        taskMapper.updateTaskEntity(task, existingTask);
        return taskRepository.save(existingTask);
    }

    /// UPDATE TASK STATUS
    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));
        existingTask.setStatus(status);
        return taskRepository.save(existingTask);
    }

    /// UPDATE TASK ASSİGNEE
    public Task updateTaskAssignee(Long taskId, Long assigneeId) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));
        AppUser assignee = appUserRepository.findById(assigneeId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + assigneeId
                ));
        existingTask.setAssignee(assignee);

        return taskRepository.save(existingTask);
    }


        ///Assignee LABEL TO TASK
        public Task assigneeLabelToTask(Long taskId, Long labelId) {
            Task existingTask = taskRepository.findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Task not found with id: " + taskId
                    ));
            Label existingLabel = labelRepository.findById(labelId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Label not found with id: " + labelId
                    ));
            existingTask.getLabels().add(existingLabel);
            return taskRepository.save(existingTask);

        }

        ///REMOVE LABEL TO TASK
        public void removeAssigneeLabelToTask(Long taskId, Long labelId) {
            Task task = taskRepository.findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Task not found with id: " + taskId
                    ));
            Label label  = labelRepository.findById(labelId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Label not found with id: " + labelId
                    ));
            task.getLabels().remove(label);

            taskRepository.save(task);
        }

    ///DELETE TASK
    public void deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()  -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));
                        taskRepository.delete(task);


    }



}

