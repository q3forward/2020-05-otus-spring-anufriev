package ru.otus.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.User;
import ru.otus.homework.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {

    private UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repo.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found by login "+login);
        }
        String roles = Optional.ofNullable(user.getRoles()).orElse("");
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
            .username(user.getLogin())
            .password(user.getPassword())
            .authorities(user.getAuthority())
            //.roles(roles.split(","))
            .build();
        return userDetails;
    }
}
