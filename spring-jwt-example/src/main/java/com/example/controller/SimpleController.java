package com.example.controller;

import com.example.model.ApiRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class SimpleController {

    @RolesAllowed(ApiRole.ROLE_ADMIN)
    @GetMapping("/admin")
    public String adminAction(){
        return "only users that has Admin role can see this";
    }

    @RolesAllowed(ApiRole.ROLE_USER)
    @GetMapping("/user")
    public String userAction(){
        return "only users that has User role can see this";
    }

    @GetMapping("/no-role")
    public String noRoleAction(){
        return "every authenticated user can see this";
    }
}
