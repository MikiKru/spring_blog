package pl.sda.mysimpleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.mysimpleblog.model.Comment;
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
    public String getPost(@PathVariable Long post_id, Model model){
        Post post = postsService.getPostById(post_id);
        model.addAttribute("post",post);
        model.addAttribute("comment",new Comment());
        return "selectedpost";
    }
    @PostMapping("/addcomment/{post_id}/{user_id}")
    public String addComment(@ModelAttribute Comment comment,
                             @PathVariable Long post_id,
                             @PathVariable Long user_id){
        postsService.addComment(comment,post_id, user_id);
        // przekierowanie na adres URL nie na nazwę widoku
        return "redirect:/post/" + post_id;
    }
}
