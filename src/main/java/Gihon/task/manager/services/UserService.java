package Gihon.task.manager.services;

import Gihon.task.manager.DTO.TaskUserSignInRequest;
import Gihon.task.manager.DTO.TaskUserSignupRequest;
import Gihon.task.manager.models.TaskUser;
import Gihon.task.manager.repositories.UserRepository;
import Gihon.task.manager.security.JwtCore;
import Gihon.task.manager.security.UserDetailsImplementation;
import Gihon.task.manager.util.SpringUtil;
import Gihon.task.manager.util.error.EmailAlreadyInUseException;
import Gihon.task.manager.util.error.UserNotCreatedException;
import Gihon.task.manager.util.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, JwtCore jwtCore) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtCore = jwtCore;
    }

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

    @Transactional
    public void signUp(TaskUserSignupRequest signupRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errorMsg =  SpringUtil.BindingResultGetError(bindingResult);
            throw new UserNotCreatedException(errorMsg);
        }
        if(this.existsByEmail(signupRequest.getEmail())){
            throw new EmailAlreadyInUseException();
        }

        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
        TaskUser user = new TaskUser();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(hashedPassword);
        this.save(user);
    }

    @Transactional
    public ResponseEntity<?> signIn(TaskUserSignInRequest signInRequest){
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        }catch (BadCredentialsException exe) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtCore.generateToken(authentication);
        return ResponseEntity.ok(jwt);
    }

}
