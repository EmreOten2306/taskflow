package tech.ekya.taskflow.task;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.project.Project;
import tech.ekya.taskflow.project.ProjectRepository;
import tech.ekya.taskflow.task.taskenums.TaskStatus;
import tech.ekya.taskflow.user.AppUser;
import tech.ekya.taskflow.user.AppUserRepository;

import java.util.List;


@Service
public class TaskService {
    private TaskRepository taskRepository;
    private ProjectRepository projectRepository;
    private TaskMapper taskMapper;
    private AppUserRepository appUserRepository;

    public TaskService(TaskRepository taskRepository
            , ProjectRepository projectRepository
            , TaskMapper taskMapper
            , AppUserRepository appUserRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
        this.appUserRepository = appUserRepository;
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

    /// GET ALL TASK
    public List<Task> getAllTasks(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Project not found with id: " + projectId
                ));
        return taskRepository.findByProjectId(projectId);
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

    ///DELETE TASK
    public void deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()  -> new ResourceNotFoundException(
                        "Task not found with id: " + taskId
                ));
                        taskRepository.delete(task);


    }



}

