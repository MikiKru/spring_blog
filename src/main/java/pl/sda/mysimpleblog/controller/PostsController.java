package pl.sda.mysimpleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String home(Model model){   // metoda
        List<Post> posts = postsService.getAllPosts();
        // przekazanie obiektu do widoku
        // model.addAttribute(nazwa w html, obiekt przekazywany)
        model.addAttribute("posts",posts);
        return "posts";     // zwracana nazwa widoku html
    }
    @GetMapping("/addpost")
    public String addPost(){
        return "addpost";
    }
    @GetMapping("/post/{post_id}")
    public String getPost(@PathVariable Long post_id){
        return "selectedpost";
    }
}
