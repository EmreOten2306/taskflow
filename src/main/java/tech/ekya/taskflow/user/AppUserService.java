package tech.ekya.taskflow.user;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.DuplicateResourceException;
import tech.ekya.taskflow.exception.ResourceNotFoundException;
import tech.ekya.taskflow.task.TaskMapper;
import tech.ekya.taskflow.task.TaskRepository;
import tech.ekya.taskflow.task.dto.TaskResponse;
import tech.ekya.taskflow.task.taskenums.TaskStatus;
import tech.ekya.taskflow.user.dto.AppUserResponse;
import tech.ekya.taskflow.user.dto.CreateAppUserRequest;
import tech.ekya.taskflow.user.dto.UpdateAppUserRequest;

import java.util.List;
@Transactional
@Service
public class AppUserService {
    private final TaskMapper taskMapper;
    private final AppUserRepository appUserRepository;
    private final TaskRepository taskRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppUserMapper appUserMapper;

    public AppUserService(AppUserRepository appUserRepository,
                          TaskRepository taskRepository,
                          PasswordEncoder passwordEncoder,
                          AppUserMapper appUserMapper,
                          TaskMapper taskMapper) {
        this.appUserRepository = appUserRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserMapper = appUserMapper;
        this.taskMapper = taskMapper;


    }
    /// CREATE USER
    public AppUserResponse createAppUser(CreateAppUserRequest request) {

        AppUser appUser = appUserMapper.toEntity(request);
        appUser.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        AppUser savedUser = appUserRepository.save(appUser);

        return appUserMapper.toResponse(savedUser);
    }


    ///GET ALL
    public List<AppUserResponse> getAllUsers(){
        return appUserRepository.findAll()
                .stream()
                .map(appUserMapper::toResponse)
                .toList();
    }

    /// GET BY ID
    public AppUserResponse getUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));

        return appUserMapper.toResponse(appUser);
    }

            ///GET USER TASKS
    public List<TaskResponse> getUserTasks(Long id) {

        appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));

        return taskRepository.findByAssigneeId(id)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }


    /// UPDATE BY ID
    public AppUserResponse updateUser(
            Long id,
            UpdateAppUserRequest request
    ) {
        AppUser existingUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));

        appUserMapper.updateEntity(request, existingUser);

        AppUser savedUser = appUserRepository.save(existingUser);

        return appUserMapper.toResponse(savedUser);
    }

    /// DELETE
    public void deleteUserById(Long id){
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id
                ));
        if (taskRepository.existsByAssigneeIdAndStatusNot(id, TaskStatus.DONE) ) {
            throw new DuplicateResourceException(
                    "User cannot be deleted because they have open tasks");
        }
        appUserRepository.deleteById(id);
    }
}
