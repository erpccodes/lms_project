package com.lms.user.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.user.model.User;
import com.lms.user.repository.UsersRepository;


@Service
public class UserService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");  // Default role
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User updateUser(String username, User userDetails) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEmail(userDetails.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        logger.debug("Loading user by username: {}", username);
        logger.debug("User found: {}", user);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getAuthorities().forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth.getAuthority())));
        return new CustomUserDetails(user);
    }
}
