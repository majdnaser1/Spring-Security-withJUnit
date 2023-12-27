package com.example.springsecurity.Service;

import com.example.springsecurity.Api.ApiException;
import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Model.Todo;
import com.example.springsecurity.Repository.AuthRepository;
import com.example.springsecurity.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    @RequiredArgsConstructor
    public class TodoService {

        private final TodoRepository todoRepository;
        private final AuthRepository authRepository;



    public List<Todo> getTodos( ) {
        return todoRepository.findAll();
    }

    public List<Todo> getTodo( Integer userId) {
            MyUser myUser=authRepository.findMyUserById(userId);
            return todoRepository.findAllByMyUser(myUser);
    }

        public void addTodo( Integer userId, Todo todo) {
            MyUser myUser=authRepository.findMyUserById(userId);
            todo.setMyUser(myUser);
            todoRepository.save(todo);
        }
    public void updateTodo(Integer id , Todo newTodo , Integer auth){
        Todo oldTodo=todoRepository.findTodoById(id);
        MyUser myUser=authRepository.findMyUserById(auth);

        if (oldTodo==null){
            throw new ApiException("Todo not found");
        }else if(oldTodo.getMyUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to update this Todo!");
        }

        newTodo.setId(id);
        newTodo.setMyUser(myUser);

        todoRepository.save(newTodo);
    }


    public Todo getTodoById(Integer id , Integer auth){
        Todo todo=todoRepository.findTodoById(id);
        if (todo==null){
            throw new ApiException("Todo not Found");
        }
        if (todo.getMyUser().getId()!=auth){
            throw new ApiException("Sorry , You do not have the authority to get this Todo!");
        }
        return todo;
    }





        public void removeTodo( Integer userId, Integer todoId) {
            Todo todo=todoRepository.findTodoById(todoId);

            if(todo.getMyUser().getId()!=userId){
                throw  new ApiException("you dont have authority");
            }

            todoRepository.delete(todo);
        }
    }


