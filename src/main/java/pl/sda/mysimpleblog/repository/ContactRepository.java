package pl.sda.mysimpleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysimpleblog.model.Contact;
import java.util.List;
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    // wartość z search = %value%
    List<Contact> findAllByEmailLikeOrMessageLikeOrNameLikeOrPhoneLike(String email, String message, String name, String phone);
}
