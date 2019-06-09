package pl.sda.mysimpleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.mysimpleblog.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
