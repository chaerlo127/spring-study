package com.example.introductionspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello "+name;
    }


    /*
    * ResponseBody가 있으면 viewResolver에 넘겨주지 않고 바로 HttpMessageConverter에 객체를 넘겨준다.
    * 문자가 넘어오면 viewResolver에서 StringHttpMessageConverter
    * 객체가 넘어오면 viewResolver에서 MappingJackon2HttpMessageConverter -> json style로 변경됨
    * 객체를 넘겨주면 json 형식으로 넘겨주는 것이 default
    * */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloAPI(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
    }

}
