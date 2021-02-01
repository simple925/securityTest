package com.securityTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    
    @GetMapping("/test")
    public void test(){

    }

    @GetMapping("/board/list")
    public String list(){
        return "board/list.html";
    }
    @GetMapping("/board/post")
    public String post() {
        return "board/post.html";
    }
}
