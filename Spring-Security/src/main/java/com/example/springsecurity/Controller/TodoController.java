package com.example.springsecurity.Controller;

import com.example.springsecurity.Api.ApiResponse;
import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Model.Todo;
import com.example.springsecurity.Service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/get-all")
    public ResponseEntity<List<Todo>> getAllTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodos());
    }

    @GetMapping("/get")
    public ResponseEntity <List<Todo>> getTodos(@AuthenticationPrincipal MyUser myUser){
        return ResponseEntity.status(HttpStatus.OK).body(todoService.getTodo(myUser.getId()));
    }

    @PostMapping()
    public ResponseEntity <ApiResponse> addTodos(@AuthenticationPrincipal MyUser myUser, @RequestBody Todo todo){
        todoService.addTodo(myUser.getId(),todo);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("New Todo added !",201));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity <ApiResponse> deleteTodo(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer todoId){
        todoService.removeTodo(myUser.getId(),todoId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Todo deleted !",200));
    }

}
