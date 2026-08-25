package tech.ekya.taskflow.user;

import org.springframework.stereotype.Service;
import tech.ekya.taskflow.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    public AppUserService(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
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
