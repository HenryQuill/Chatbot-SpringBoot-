package com.example.chatbotv2.services;

import com.example.chatbotv2.models.User;
import com.example.chatbotv2.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;
    public UserDetailsServiceImp(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username " + username + " not found"));
        return UserDetailsImp.build(user);
    }

}
