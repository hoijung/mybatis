
package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController2 {

    @Autowired
    private UserService2 userService;

    @GetMapping("/users2")
    public List<Map<String, Object>> users() {
        return userService.selectUser();
    }
}