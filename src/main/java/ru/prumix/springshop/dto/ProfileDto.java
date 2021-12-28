package ru.prumix.springshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.prumix.springshop.entities.Role;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String username;
    private String password;
    private String email;
    private Collection<Role> roles;

}
