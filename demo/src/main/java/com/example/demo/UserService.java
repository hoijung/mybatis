package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserMapper;

@Service
public class UserService {

    private final UserMapper userMapper;

    UserService(UserMapper userMapper) {
    	
    	
        this.userMapper = userMapper;
    }

    public List<Map<String, Object>> selectUser() {
        return userMapper.selectUser();
    }
}