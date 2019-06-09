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
import pl.sda.mysimpleblog.model.enums.CategoryEnum;
import pl.sda.mysimpleblog.service.PostsService;

import java.util.ArrayList;
import java.util.Arrays;
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
    @GetMapping("/addpost")
    public String addPost(Model model){
        model.addAttribute("post",new Post());
        List<CategoryEnum> categories =
                new ArrayList<>(Arrays.asList(CategoryEnum.values()));
        System.out.println(categories);
        // przekazanie listy kategorii do znacznika SELECT
        model.addAttribute("categories", categories);
        return "addpost";
    }
    @PostMapping("/addpost")
    public String addPost(@ModelAttribute Post post){
        postsService.savePost(post);
        return "redirect:/";
    }
}
