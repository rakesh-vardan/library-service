package com.epam.jpop.libraryservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class HealthCheckController {

    @GetMapping("/")
    public String health(){
        return "Hello World!";
    }
}
