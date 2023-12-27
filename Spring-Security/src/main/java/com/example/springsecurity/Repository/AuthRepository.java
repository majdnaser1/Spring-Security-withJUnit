package com.example.springsecurity.Repository;

import com.example.springsecurity.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface

AuthRepository extends JpaRepository<MyUser,Integer> {
    MyUser findMyUserById(Integer id);

    MyUser findMyUserByUsername(String username);
}
