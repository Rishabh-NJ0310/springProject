package com.project.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestBoot {
    
    @GetMapping("/get")
    public String getMethodName() {
        return "Hello World";
    }
    
}
