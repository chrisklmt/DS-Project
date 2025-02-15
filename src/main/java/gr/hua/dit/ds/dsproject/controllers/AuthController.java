package gr.hua.dit.ds.dsproject.controllers;

import gr.hua.dit.ds.dsproject.entities.Role;
import gr.hua.dit.ds.dsproject.entities.User;
import gr.hua.dit.ds.dsproject.repositories.RoleRepository;
import gr.hua.dit.ds.dsproject.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.annotation.PostConstruct;
import java.util.Optional;

@Controller
public class AuthController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AuthController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setup() {
        // Check if roles exist, otherwise create them
        createRoleIfNotExists("ROLE_CLIENT");
        createRoleIfNotExists("ROLE_FREELANCER");
        createRoleIfNotExists("ROLE_ADMIN");

        // Add secret admin user if not already present
        Optional<User> secretAdmin = userRepository.findByUsername("admin");
        if (secretAdmin.isEmpty()) {
            createSecretAdminUser();
        }
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

    private void createSecretAdminUser() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User admin = new User("admin", "admin@example.com", encoder.encode("admin"));
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        admin.getRoles().add(adminRole);
        userRepository.save(admin);
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
}
