package com.example.introductionspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    /*
    * web application 에서 /hello를 호출하면 이 method가 호출 됨
    * key는 data, 값은 hello
   * */
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello";
    }

}
