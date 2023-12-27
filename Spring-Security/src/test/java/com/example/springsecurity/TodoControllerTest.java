package com.example.springsecurity;

import com.example.springsecurity.Api.ApiResponse;
import com.example.springsecurity.Controller.TodoController;
import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Model.Todo;
import com.example.springsecurity.Service.TodoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TodoController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class TodoControllerTest {
    @MockBean
    TodoService todoService;

    @Autowired
    MockMvc mockMvc;

    Todo todo1,todo2,todo3;
    MyUser myUser;


    List<Todo> todos,todoList;

    @BeforeEach
    void setUp() {
        myUser=new MyUser(1,"Maha" , "12345" , "ADMIN" , null);
        todo1 = new Todo(1, "todo1", myUser );
        todo2 = new Todo(2 , "todo2",  myUser );
        todo3 = new Todo(3 , "todo3", myUser );
        todos= Arrays.asList(todo1);



        todoList=Arrays.asList(todo2);

    }

    @Test
    public void GetAllTodo() throws Exception {
        Mockito.when(todoService.getTodos()).thenReturn(todos);
        mockMvc.perform(get("/api/v1/todo/get-all"))
        .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("todo1"));
    }

    @Test
    public void testAddTodo() throws  Exception {
        mockMvc.perform(post("/api/v1/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(todo1)))
                        .andExpect(status().isOk());

    }

    @Test
    public void testDeleteTodo() throws Exception{
        mockMvc.perform(delete("/api/v1/todo/{todoId}",todo1.getId()))
                .andExpect(status().isOk());

    }


}
