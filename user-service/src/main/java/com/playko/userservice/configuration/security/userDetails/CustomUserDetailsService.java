package com.playko.userservice.configuration.security.userDetails;


import com.playko.userservice.model.RoleModel;
import com.playko.userservice.model.UserModel;
import com.playko.userservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = this.userRepository.findByEmail(email);

        List<UserModel> userEntity = userRepository.findAllById(Collections.singleton(user.getId()));

        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("Invalid email or password");
        }

        List<RoleModel> roles = new ArrayList<>();

        for (UserModel usuario : userEntity) {
            roles.add(user.getRole());
        }
        return CustomUserDetails.build(user, roles);
    }
}
