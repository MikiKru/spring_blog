package pl.sda.mysimpleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysimpleblog.model.Contact;
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
