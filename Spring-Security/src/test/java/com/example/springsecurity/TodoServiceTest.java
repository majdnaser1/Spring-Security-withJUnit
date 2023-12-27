package com.example.springsecurity;

import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Model.Todo;
import com.example.springsecurity.Repository.AuthRepository;
import com.example.springsecurity.Repository.TodoRepository;
import com.example.springsecurity.Service.TodoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {

    @InjectMocks
    TodoService todoService;
    @Mock
    TodoRepository todoRepository;
    @Mock
    AuthRepository authRepository;

    MyUser user;

    Todo todo1,todo2,todo3;

    List<Todo> todos;

    @BeforeEach
    void setUp() {
        user=new MyUser(null,"majd","123","Admin", null);
        todo1=new Todo(null,"todo1",user);
        todo2=new Todo(null,"todo2",user);
        todo3=new Todo(null,"todo3",null);

        todos=new ArrayList<>();
        todos.add(todo1);
        todos.add(todo2);
        todos.add(todo3);
    }


    @Test
    public void getAllTodoTest(){
        when(todoRepository.findAll()).thenReturn(todos);
        List<Todo> todoList=todoService.getTodos();
        Assertions.assertEquals(3,todoList.size());
        verify(todoRepository,times(1)).findAll();

    }
    @Test
    public void getTodoByIdTest(){
        when(authRepository.findMyUserById(user.getId())).thenReturn(user);
        when(todoRepository.findAllByMyUser(user)).thenReturn(todos);


        List<Todo> todoList = todoService.getTodo(user.getId());
        Assertions.assertEquals(todoList,todos);
        verify(authRepository,times(1)).findMyUserById(user.getId());
        verify(todoRepository,times(1)).findAllByMyUser(user);

    }

    @Test
    public void AddTodoTest(){

        when(authRepository.findMyUserById(user.getId())).thenReturn(user);

        todoService.addTodo(user.getId(),todo3);
        verify(authRepository,times(1)).findMyUserById(user.getId());
        verify(todoRepository,times(1)).save(todo3);
    }

    @Test
    public void updateTodoTest(){

        when(todoRepository.findTodoById(todo1.getId())).thenReturn(todo1);
        when(authRepository.findMyUserById(user.getId())).thenReturn(user);

        todoService.updateTodo(todo1.getId(),todo2,user.getId());

        verify(todoRepository,times(1)).findTodoById(todo1.getId());
        verify(authRepository,times(1)).findMyUserById(user.getId());
        verify(todoRepository,times(1)).save(todo2);

    }







}
