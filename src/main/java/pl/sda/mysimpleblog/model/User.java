package pl.sda.mysimpleblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id                                             // PK
    @GeneratedValue(strategy = GenerationType.AUTO) // AI
    private Long id;
    @Email(message = "Invalid e-mail address")
    @NotBlank(message = "E-mail can't be null")
    private String email;
    @NotBlank(message = "Password can't be null")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
    private LocalDateTime register_date = LocalDateTime.now();
    private boolean activity = true;
    @Transient
    @NotBlank(message = "Password can't be null")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password_confirm;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    @ManyToMany(cascade = CascadeType.ALL,
                fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // metoda do aktualizacji ról użtrkownika
    public void addRole(Role role){
        this.roles.add(role);
    }
}
