package org.example;

import org.example.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;

    public AuthServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean register(String login, String rawPassword, String role) {
        if (userRepo.findAll().stream().anyMatch(u -> u.getLogin().equals(login))) {
            System.out.println("Użytkownik o tym loginie już istnieje.");
            return false;
        }

        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        User user = User.builder()
                .id(java.util.UUID.randomUUID().toString())
                .login(login)
                .password(hashed)
                .role(role)
                .build();

        userRepo.save(user);
        System.out.println("Zarejestrowano pomyślnie.");
        return true;
    }

    @Override
    public Optional<User> login(String login, String rawPassword) {
        return userRepo.findAll().stream()
                .filter(u -> u.getLogin().equals(login))
                .filter(u -> BCrypt.checkpw(rawPassword, u.getPassword()))
                .findFirst();
    }
}
