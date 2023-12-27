package com.example.springsecurity.Controller;

import com.example.springsecurity.Api.ApiResponse;
import com.example.springsecurity.Model.MyUser;
import com.example.springsecurity.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody MyUser myUser){
        authService.register(myUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User registered !",201));
    }

    @PostMapping("/admin")
    public ResponseEntity<ApiResponse> admin(){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Welcome back ADMIN",200));
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse> user(){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Welcome back user",200));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Welcome back",200));
    }
}
