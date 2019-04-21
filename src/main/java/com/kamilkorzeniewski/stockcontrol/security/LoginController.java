package com.kamilkorzeniewski.stockcontrol.security;

import com.kamilkorzeniewski.stockcontrol.security.token.TokenService;
import com.kamilkorzeniewski.stockcontrol.security.user.User;
import com.kamilkorzeniewski.stockcontrol.security.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;
    private final TokenService tokenService;

    LoginController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<String> login(@RequestBody AuthenticationDTO dto) {
        String userName = dto.getUserName();
        User user = Optional.ofNullable(userService.findUserByName(userName))
                .orElseThrow(() -> new UsernameNotFoundException("Not found user for user name : " + userName));

        if (userService.isPasswordCorrect(user, dto.getPassword())) {
            String tokenValue = tokenService.generateToken(user).getToken();
            return new ResponseEntity<>(tokenValue, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<User> register(@RequestBody AuthenticationDTO dto) {
        User user = userService.saveUser(dto.toUser());
        LOGGER.info("User " + user.toString() + " has been created !");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
