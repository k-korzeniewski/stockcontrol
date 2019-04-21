package com.kamilkorzeniewski.stockcontrol;

import com.kamilkorzeniewski.stockcontrol.security.user.User;
import com.kamilkorzeniewski.stockcontrol.security.user.UserRole;
import com.kamilkorzeniewski.stockcontrol.security.user.UserRoles;
import com.kamilkorzeniewski.stockcontrol.security.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class UserCommandLineRunner implements CommandLineRunner {
    private final Logger logger = LoggerFactory.getLogger(UserCommandLineRunner.class);

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    public UserCommandLineRunner(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length <= 0)
            return;
        if (args[0].equals("user")) {
            if (args.length < 2) {
                logger.warn("argument required");
                return;
            }
            if (args[1].equals("create")) {
                if (args.length != 4) {
                    logger.warn("user name and password required");
                    return;
                }
                String userName = args[2];
                String password = args[3];
                UserRole role = new UserRole(UserRoles.USER);
                User user = new User(userName, encoder.encode(password), new HashSet<>(List.of(role)));
                userService.saveUser(user);
                logger.info("USER " + userName + " saved !");
            }



        }
    }
}
