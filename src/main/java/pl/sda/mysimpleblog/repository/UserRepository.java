package pl.sda.mysimpleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysimpleblog.model.User;

@Repository                                        // model,typPK
public interface UserRepository extends JpaRepository<User,Long> {
    User getByEmail(String email);
}
