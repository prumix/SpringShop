package ru.prumix.springshop.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.prumix.springshop.dto.ProfileDto;
import ru.prumix.springshop.entities.Role;
import ru.prumix.springshop.entities.User;
import ru.prumix.springshop.services.UserService;

import java.util.Collection;
import java.util.HashSet;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping()
    public User createUser(@RequestBody ProfileDto newUser)  {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        System.out.println(newUser);
       return userService.createUser(newUser);

    }
}
