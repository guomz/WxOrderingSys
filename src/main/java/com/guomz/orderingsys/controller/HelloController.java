package com.guomz.orderingsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/sayhello")
    public String sayHello(HttpServletRequest request){
        request.setAttribute("name", "guomz");
        return "hello";
    }
}
