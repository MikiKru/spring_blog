package pl.sda.mysimpleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.mysimpleblog.model.Contact;
import pl.sda.mysimpleblog.service.AutoMailingService;
import pl.sda.mysimpleblog.service.ContactService;

import javax.validation.Valid;

@Controller
public class ContactController {

    ContactService contactService;
    AutoMailingService autoMailingService;
    @Autowired
    public ContactController(ContactService contactService, AutoMailingService autoMailingService) {
        this.contactService = contactService;
        this.autoMailingService = autoMailingService;
    }

    @GetMapping("/contact")
    public String contact(Model model, Authentication auth){
        Contact contact = new Contact();
        if(auth != null){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            contact.setEmail(userDetails.getUsername());
        }
        model.addAttribute("contact", contact);
        return "contact";
    }
    @PostMapping("/contact")
    public String contact(
            @ModelAttribute @Valid Contact contact,
            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "contact";
        }
        // zapis do db
        contactService.addContact(contact);
        // wysłanie auto-mialingu
        autoMailingService.sendMessage(
                contact.getEmail(),
                "Potwierdzenie wysłania wiadomości",
                "Dziękujemy za wysłanie wiadomości.");
        return "redirect:/";
    }
}
