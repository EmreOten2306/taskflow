package tech.ekya.taskflow.task;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    private  TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping ("/projects/{projectId}/tasks")
    public Task createTask(@RequestBody Task task,
                           @PathVariable Long projectId){
        return taskService.createTask(projectId, task);
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
    public Task updateTask(@PathVariable Long id,@RequestBody Task task){
    return taskService.updateTaskById(id,task);

    }

    @DeleteMapping ("/tasks/{id}")
    public void deleteTaskById(@PathVariable Long id){
        taskService.deleteTaskById(id);
    }

}
