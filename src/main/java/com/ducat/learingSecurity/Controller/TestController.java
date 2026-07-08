package com.ducat.learingSecurity.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController{
    @GetMapping("/test")
    public String HealthEndpoint(){
        return "Hello! ";
    }
    //api -->secure nahi hogne 
}