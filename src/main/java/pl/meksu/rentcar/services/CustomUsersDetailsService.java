package pl.meksu.rentcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.meksu.rentcar.models.User;
import pl.meksu.rentcar.repo.UserRepository;

import java.util.ArrayList;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByMail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getMail(), user.getPassword(), new ArrayList<>());
    }
}
