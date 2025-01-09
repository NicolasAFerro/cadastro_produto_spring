package com.nicolas.product_backend.resources_or_controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {

    @GetMapping("Hello")
    public String getHello() {
        return "Hello Spring boot Aqui";
    }
}
