package pl.sda.mysimpleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.mysimpleblog.model.User;
import pl.sda.mysimpleblog.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        return "adduser";
    }
    @PostMapping("/register")
    public String addUser(
            @ModelAttribute @Valid User user,
            BindingResult bindingResult,
            Model model){

        if(bindingResult.hasErrors()){
            return "adduser";
        }
        // sprawdzenie haseł
        if(userService.paswordCheck(user.getPassword(),user.getPassword_confirm())){
            // rejestracja
            userService.registerUser(user);
            return "redirect:/";
        }
        model.addAttribute(                                             // przekazanie do widoku komentarza dot. porównania haseł
                "passwordMessage", "Passwords not matched!");
        return "adduser";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
