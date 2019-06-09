package pl.sda.mysimpleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.sda.mysimpleblog.model.Post;
import pl.sda.mysimpleblog.service.PostsService;
import java.util.List;
@Controller
public class PostsController {
    PostsService postsService;
    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }
    @GetMapping("/")        // adres url
    public String home(){   // metoda
        List<Post> posts = postsService.getAllPosts();
        System.out.println(posts);
        return "posts";     // zwracana nazwa widoku html
    }
    @GetMapping("/addpost")
    public String addPost(){
        return "addpost";
    }
}
