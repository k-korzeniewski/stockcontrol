package com.kamilkorzeniewski.stockcontrol.security.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User saveUser(User user) {
        user = user.withPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UserRole userRole = new UserRole(UserRoles.USER);
        user = user.withRoles(new HashSet<>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public boolean isPasswordCorrect(User user, String password) {
        return bCryptPasswordEncoder.matches(password, user.getPassword());
    }
}
