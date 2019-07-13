package pl.sda.mysimpleblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.mysimpleblog.model.Role;
import pl.sda.mysimpleblog.model.User;
import pl.sda.mysimpleblog.repository.RoleRepository;
import pl.sda.mysimpleblog.repository.UserRepository;
import java.util.Set;
import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean paswordCheck(String password, String password_confirm){
        if(password.equals(password_confirm)){
            return true;
        }
        return false;
    }
    public void registerUser(User user){
        user.addRole(roleRepository.getOne(1L));
        user.setPassword(passwordEncoder.encode(user.getPassword()));   // szyfrowanie hasła
        userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public Role getAdminRole(){
        return roleRepository.getOne(2L);
    }
}
