package pl.sda.mysimpleblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.sda.mysimpleblog.service.ContactService;

@Controller
public class AdminController {

    ContactService contactService;
    @Autowired
    public AdminController(ContactService contactService) {
        this.contactService = contactService;
    }
    @GetMapping("/admin")
    public String adminPanel(Model model){
        model.addAttribute("contacts",contactService.getAllContacts());
        return "admin/tables";
    }
    @GetMapping("/admin/changeStatus/{contact_id}")
    public String changeStatus(@PathVariable Long contact_id, Model model){
        contactService.changeStatus(contact_id);
        model.addAttribute("contacts",contactService.getAllContacts());
        return "admin/tables";
    }

}
