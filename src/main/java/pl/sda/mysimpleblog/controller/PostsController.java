package pl.sda.mysimpleblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostsController {
    @GetMapping("/")        // adres url
    public String home(){   // metoda
        return "posts";     // zwracana nazwa widoku html
    }
}
