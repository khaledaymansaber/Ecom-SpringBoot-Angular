package com.ecom.security;

import com.ecom.entity.AppUser;
import com.ecom.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        AppUser user = userRepository.findByEmail(usernameOrEmail)
                .or(() -> userRepository.findByUserName(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email/username: " + usernameOrEmail));

        return new UserPrincipal(user);
    }
}
