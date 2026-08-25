package tech.ekya.taskflow.user;

import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.task.Task;
import tech.ekya.taskflow.task.TaskRepository;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final TaskRepository taskRepository;

    public AppUserService(AppUserRepository appUserRepository, TaskRepository taskRepository) {
        this.appUserRepository = appUserRepository;
        this.taskRepository = taskRepository;
    }

    ///GET ALL
    public List<AppUser> getAllUsers(){
        return appUserRepository.findAll();
    }

    ///GET BY ID
    public AppUser getUserById(Long id){
        return appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));
    }


    ///GET USER TASK
    public List<Task> getUserTasks(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));
        return taskRepository.findByAssigneeId(id);
    }


    ///UPDATE BY ID
    public AppUser updateUser(Long id ,AppUser appUser){
        AppUser existingUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));
        existingUser.setFullName(appUser.getFullName());
        existingUser.setEmail(appUser.getEmail());
        existingUser.setRole(appUser.getRole());
        existingUser.setPasswordHash(appUser.getPasswordHash());

        return appUserRepository.save(existingUser);
    }

    /// DELETE
    public void deleteUserById(Long id){
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));
        appUserRepository.deleteById(id);
    }
}
