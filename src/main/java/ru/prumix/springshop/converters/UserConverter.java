package ru.prumix.springshop.converters;

import org.springframework.stereotype.Component;
import ru.prumix.springshop.dto.ProfileDto;
import ru.prumix.springshop.entities.User;

@Component
public class UserConverter {
    public User dtoToEntity(ProfileDto profileDto) {
        User user = new User();
        user.setUsername(profileDto.getUsername());
        user.setPassword(profileDto.getPassword());
        user.setEmail(profileDto.getEmail());
        return user;
    }

    public ProfileDto entityToDto(User user) {
        return new ProfileDto(user.getUsername(), user.getPassword(), user.getEmail(), user.getRoles());
    }
}
