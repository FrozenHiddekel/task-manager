package Gihon.task.manager.services;

import Gihon.task.manager.models.TaskUser;
import Gihon.task.manager.repositories.UserRepository;
import Gihon.task.manager.security.UserDetailsImplementation;
import Gihon.task.manager.util.error.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public List<TaskUser> findAll(){
        return userRepository.findAll();
    }


    public TaskUser findById(int id){
        Optional<TaskUser> foundUser = userRepository.findById(id);
        return foundUser.orElseThrow(UserNotFoundException::new);
    }

    public TaskUser findByEmail(String email){
        Optional<TaskUser> foundUser = userRepository.findByEmail(email);
        return foundUser.orElseThrow(UserNotFoundException::new);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public void save(TaskUser user){
        userRepository.save(user);
    }


    @Transactional
    public void deleteById(int id){
        Optional<TaskUser> foundUser = userRepository.findById(id);
        userRepository.delete(foundUser.orElseThrow(UserNotFoundException::new));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TaskUser user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)
        ));
        return UserDetailsImplementation.build(user);
    }
}
