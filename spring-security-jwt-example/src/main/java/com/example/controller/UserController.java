package com.example.controller;

import com.example.entity.UserDAO;
import com.example.entity.UserDTO;
import com.example.entity.exception.UsernameAlreadyExistException;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        UserDAO user = userService.findOneByUsername(userDTO.getUsername());

        if (user != null) {
            throw new UsernameAlreadyExistException();
        }

        return ResponseEntity.ok(userService.save(userDTO));
    }
}
