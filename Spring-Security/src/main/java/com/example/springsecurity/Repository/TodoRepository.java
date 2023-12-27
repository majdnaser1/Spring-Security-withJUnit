package com.example.springsecurity.Repository;

import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo,Integer> {
    Todo findTodoById(Integer id);

    List<Todo> findAllByMyUser(MyUser myUser);




}
