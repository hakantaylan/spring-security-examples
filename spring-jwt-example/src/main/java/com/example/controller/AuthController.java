package com.example.controller;

import com.example.model.AuthRequest;
import com.example.util.JwtTokenUtil;
import com.example.repository.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final JwtTokenUtil tokenUtil;

    private final UserRepo userRepo;


    public AuthController(JwtTokenUtil tokenUtil, UserRepo userRepo) {
        this.tokenUtil = tokenUtil;
        this.userRepo = userRepo;
    }

    @PostMapping("/auth/authenticate")
    public String Authenticate(@RequestBody AuthRequest request){
        var userDetail = this.userRepo.findByUsernameAndPassword(request.getUsername(), request.getPassword()).orElseThrow();
        var token = tokenUtil.generateJwtToken(userDetail);
        return token;
    }

    @GetMapping("/auth/me")
    public UserDetails Me(){
        var detail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return detail;
    }
}
