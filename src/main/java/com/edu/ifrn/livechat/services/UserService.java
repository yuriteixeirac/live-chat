package com.edu.ifrn.livechat.services;

import com.edu.ifrn.livechat.DTOs.TokenDTO;
import com.edu.ifrn.livechat.DTOs.UserDTO;
import com.edu.ifrn.livechat.exceptions.InvalidCredentialsException;
import com.edu.ifrn.livechat.exceptions.UserAlreadyExistsException;
import com.edu.ifrn.livechat.models.User;
import com.edu.ifrn.livechat.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public TokenDTO register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Usuário com esse e-mail já existe.");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return jwtService.createToken(user.getUsername());
    }

    public TokenDTO login(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername())
                .filter(u -> encoder.matches(user.getPassword(), u.getPassword()))
                .orElseThrow(() -> new InvalidCredentialsException("Credenciais inválidas."));

        return jwtService.createToken(user.getUsername());
    }
}
