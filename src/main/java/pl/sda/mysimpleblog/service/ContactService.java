package pl.sda.mysimpleblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.mysimpleblog.model.Contact;
import pl.sda.mysimpleblog.repository.ContactRepository;

@Service
public class ContactService {
    ContactRepository contactRepository;
    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
    public void addContact(Contact contact){
        contactRepository.save(contact);
    }
}
