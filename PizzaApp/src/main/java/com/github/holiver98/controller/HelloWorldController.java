package com.github.holiver98.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/helloworld")
public class HelloWorldController {

    @RequestMapping(value = "/hello")
    public @ResponseBody String hello(){
        return "hello";
    }
}
