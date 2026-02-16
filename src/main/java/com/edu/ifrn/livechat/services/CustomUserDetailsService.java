package com.edu.ifrn.livechat.services;

import com.edu.ifrn.livechat.models.CustomUserDetails;
import com.edu.ifrn.livechat.repositories.UserRepository;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return new CustomUserDetails(repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."))
        );
    }
}
