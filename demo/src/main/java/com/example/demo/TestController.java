package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private MyBatisXmlReloader xmlReloader;
    

//    @Autowired
//    @Qualifier("cssDataSource")
//    private DataSource dataSource;


    @GetMapping("/reload")
    public String reload() throws Exception {

        xmlReloader.reload("mapper/UserMapper.xml");

        return "RELOAD OK";
    }
}