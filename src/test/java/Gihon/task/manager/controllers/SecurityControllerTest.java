package Gihon.task.manager.controllers;

import Gihon.task.manager.DTO.TaskUserSignInRequest;
import Gihon.task.manager.DTO.TaskUserSignupRequest;
import Gihon.task.manager.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class SecurityControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Validator validator;

    @Test
    void signUpInvalidEmail() throws Exception {
        TaskUserSignupRequest signupRequest = new TaskUserSignupRequest();
        signupRequest.setName("Igor");
        signupRequest.setEmail("sobak@sobaka.com");
        signupRequest.setPassword("testpass");
        if (validator.validateObject(signupRequest).hasErrors()){
            return;
        }
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void signUpInvalidName() throws Exception {
        TaskUserSignupRequest signupRequest = new TaskUserSignupRequest();
        signupRequest.setName("I");
        signupRequest.setEmail("sobak@sobaka.com");
        signupRequest.setPassword("testpass");
        if (validator.validateObject(signupRequest).hasErrors()){
            return;
        }
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void signUp() throws Exception {
        TaskUserSignupRequest signupRequest = new TaskUserSignupRequest();
        signupRequest.setName("Igor");
        signupRequest.setEmail("sobak@sobaka.com");
        signupRequest.setPassword("testpass");
        if (validator.validateObject(signupRequest).hasErrors()){
            return;
        }
        String jsonRequest = objectMapper.writeValueAsString(signupRequest);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void signInNotFound() throws Exception {
        TaskUserSignInRequest signInRequest = new TaskUserSignInRequest();
        signInRequest.setEmail("sobak@sobaka.com");
        signInRequest.setPassword("testpass");
        Mockito.when(userService.signIn(signInRequest)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        String jsonRequest = objectMapper.writeValueAsString(signInRequest);
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    void signIn() throws Exception {
        TaskUserSignInRequest signInRequest = new TaskUserSignInRequest();
        signInRequest.setEmail("sobak@sobaka.com");
        signInRequest.setPassword("testpass");
        Mockito.when(userService.signIn(signInRequest)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        String jsonRequest = objectMapper.writeValueAsString(signInRequest);
        mockMvc.perform(post("/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }
}
