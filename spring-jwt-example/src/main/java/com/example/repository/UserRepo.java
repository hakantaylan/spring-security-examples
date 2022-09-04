package com.example.repository;

import com.example.model.ApiRole;
import com.example.model.ApiUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepo {

    private static final List<ApiUser> users = List.of(
            new ApiUser("adminuser","test", List.of(new SimpleGrantedAuthority(ApiRole.ROLE_PREFIX + ApiRole.ROLE_ADMIN),
                    new SimpleGrantedAuthority(ApiRole.ROLE_PREFIX + ApiRole.ROLE_USER))),
            new ApiUser("user","test", List.of(new SimpleGrantedAuthority(ApiRole.ROLE_PREFIX + ApiRole.ROLE_USER))),
            new ApiUser("noroleuser","test", List.of())
    );

    public Optional<ApiUser> findByUsernameAndPassword(String username, String password) {
        return users.stream().filter(x->x.getUsername().equals(username) && x.getPassword().equals(password))
                .findFirst();
    }
}
