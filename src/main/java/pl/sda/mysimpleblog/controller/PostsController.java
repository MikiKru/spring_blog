package pl.sda.mysimpleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.mysimpleblog.model.Comment;
import pl.sda.mysimpleblog.model.Post;
import pl.sda.mysimpleblog.model.enums.CategoryEnum;
import pl.sda.mysimpleblog.service.PostsService;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PostsController {
    PostsService postsService;
    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }
    @GetMapping("/")        // adres url
    public String home(Model model, Authentication auth){   // metoda
        if(auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            // przekazuję do widoku email zalogowanego użytkownika
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postsService.isAdmin(userDetails));
        }
        List<Post> posts = postsService.getAllPosts();
        // wydobycie listy kategorii
        Set<CategoryEnum> categories = new HashSet<>();
        for (Post post : posts) {
            categories.add(post.getCategory());
        }
        // przekazanie listy kategorii do widoku
        model.addAttribute("categories", categories);
        // przekazanie obiektu do widoku
        // model.addAttribute(nazwa w html, obiekt przekazywany)
        model.addAttribute("posts",posts);
        return "posts";     // zwracana nazwa widoku html
    }
    @GetMapping("/filter_category{category}")
    public String filterCategories(@PathVariable CategoryEnum category, Model model){
        List<Post> posts = postsService.filterByCategory(category);
        model.addAttribute("posts",posts);
        return "posts";
    }
    @GetMapping("/post/{post_id}")
    public String getPost(
            @PathVariable Long post_id,
            Model model,
            Authentication auth){
        if(auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            model.addAttribute("loggedEmail", userDetails.getUsername());
            model.addAttribute("isAdmin", postsService.isAdmin(userDetails));
        }
        Post post = postsService.getPostById(post_id);
        // sortowanie za pomoca stream API
        post.setComments(post.getComments()
                .stream()
                .sorted(Comparator.comparing(Comment::getId).reversed())
                .collect(Collectors.toList())
                );
        model.addAttribute("post",post);
        model.addAttribute("comment",new Comment());
        return "selectedpost";
    }
    @PostMapping("/addcomment/{post_id}/{user_id}")
    public String addComment(@ModelAttribute Comment comment,
                             @PathVariable Long post_id,
                             @PathVariable Long user_id,
                             Authentication auth){
        postsService.addComment(comment,post_id, auth);
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
    public String addPost(@ModelAttribute Post post, Authentication authentication){
        UserDetails loggedUserDetails = (UserDetails) authentication.getPrincipal();
        postsService.savePost(post, loggedUserDetails.getUsername());
        return "redirect:/";
    }
    @DeleteMapping("/delete/{post_id}")
    public String deletePost(@PathVariable Long post_id){
        postsService.deletePost(post_id);
        return "redirect:/";
    }
    @GetMapping("/update/{post_id}")
    public String updatePost(@PathVariable Long post_id, Model model){
        // pobranie istniejącego posta
        Post post = postsService.getPostById(post_id);
        // przekazanie istniejącego posta do formularza modyfikacji
        model.addAttribute("post", post);
        List<CategoryEnum> categories =
                new ArrayList<>(Arrays.asList(CategoryEnum.values()));
        System.out.println(categories);
        // przekazanie listy kategorii do znacznika SELECT
        model.addAttribute("categories", categories);
        return "updatepost";
    }

    @PutMapping("/update/{post_id}")
    public String updatePost(@PathVariable Long post_id, @ModelAttribute Post post){
        postsService.updatePost(post_id, post);
        return "redirect:/";
    }
    @DeleteMapping("/delete_comment/{comment_id}")
    public String deleteComment(@PathVariable Long comment_id){
        // wydobycie id posta z komentarza
        Long post_id = postsService.getPostIdByCommentId(comment_id);
        // usuwanie komentarza po id
        postsService.deleteComment(comment_id);
        return "redirect:/post/" + post_id;
    }
    @GetMapping("/post_like/{post_id}")
    public String likePost(@PathVariable Long post_id){
        postsService.likePost(post_id);
        return "redirect:/post/" + post_id;
    }
    @GetMapping("/post_not_like/{post_id}")
    public String notLikePost(@PathVariable Long post_id){
        postsService.notLikePost(post_id);
        return "redirect:/post/" + post_id;
    }
}
