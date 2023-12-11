package Gihon.task.manager.controllers;


import Gihon.task.manager.DTO.TaskUserSignInRequest;
import Gihon.task.manager.DTO.TaskUserSignupRequest;
import Gihon.task.manager.models.TaskUser;
import Gihon.task.manager.security.JwtCore;
import Gihon.task.manager.services.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Setter
@AllArgsConstructor

@RestController
@RequestMapping("/auth")
public class SecurityController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;


    @PostMapping("/signup")
    @ApiResponse(description = "Регистрация пользователя")
    public ResponseEntity<?> signup(@RequestBody TaskUserSignupRequest signupRequest){
        if(userService.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email is already in use");
        }

        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
        TaskUser user = new TaskUser();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(hashedPassword);
        userService.save(user);
        return ResponseEntity.ok("Registration was successful");
    }

    @PostMapping("/signin")
    @ApiResponse(description = "Авторизация пользователя")
    ResponseEntity<?> signIn(@RequestBody TaskUserSignInRequest signInRequest){
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
