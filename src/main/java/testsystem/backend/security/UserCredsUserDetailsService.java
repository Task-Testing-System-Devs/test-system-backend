package testsystem.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import testsystem.backend.model.user.User;
import testsystem.backend.repository.user.UserRepository;

import java.util.Optional;

@Component
public class UserCredsUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(UserCredsUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found"));
    }

}
