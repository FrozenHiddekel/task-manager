package Gihon.task.manager.controllers;


import Gihon.task.manager.DTO.TaskUserSignInRequest;
import Gihon.task.manager.DTO.TaskUserSignupRequest;
import Gihon.task.manager.security.JwtCore;
import Gihon.task.manager.services.UserService;
import Gihon.task.manager.util.error.CustomErrorResponse;
import Gihon.task.manager.util.error.EmailAlreadyInUseException;
import Gihon.task.manager.util.error.UserNotCreatedException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> signup(@RequestBody @Valid TaskUserSignupRequest signupRequest, BindingResult bindingResult){
        try {
            userService.signUp(signupRequest, bindingResult);
        }catch (Exception e){
            throw e;
        }
        return ResponseEntity.ok("Registration was successful");
    }

    @PostMapping("/signin")
    @ApiResponse(description = "Авторизация пользователя")
    ResponseEntity<?> signIn(@RequestBody TaskUserSignInRequest signInRequest){
       return userService.signIn(signInRequest);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(BadCredentialsException e){
        CustomErrorResponse response = new CustomErrorResponse("Invalid username or password");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(EmailAlreadyInUseException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(UnexpectedRollbackException e){
        CustomErrorResponse response = new CustomErrorResponse("Invalid username or password");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CustomErrorResponse> handleException(UserNotCreatedException e){
        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
