package Gihon.task.manager.controllers;

import Gihon.task.manager.models.TaskUser;
import Gihon.task.manager.services.UserService;
import Gihon.task.manager.util.error.UserNotFoundException;
import Gihon.task.manager.util.error.CustomErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@AllArgsConstructor
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    private final UserService userService;
//
//    @GetMapping
//    public List<TaskUser> getUser(){
//        return userService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public TaskUser getUser(@PathVariable("id") int id){
//        return userService.findById(id);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable("id") int id){
//        userService.deleteById(id);
//    }
//
//    @ExceptionHandler
//    private ResponseEntity<CustomErrorResponse> handleException(UserNotFoundException e){
//        CustomErrorResponse response = new CustomErrorResponse(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
////    @ResponseBody
////    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
////    public String handleHttpMediaTypeNotAcceptableException() {
////        return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
////    }
//}
