package com.example.newsample.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/world")
    public String helloWorld(){
        return "Hello world!";
    }

    @GetMapping("/you")
    public String helloYou(@RequestParam("name") String name){
        return "Hello "+name+"!";
    }
}
