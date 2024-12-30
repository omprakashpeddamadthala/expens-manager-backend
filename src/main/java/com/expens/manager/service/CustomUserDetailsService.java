package com.expens.manager.service;

import com.expens.manager.entity.ProfileEntity;
import com.expens.manager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * This class is used to load user details from database
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-12-30
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    /**
     * This method is used to load user details from database
     * @param email
     * @return userDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity profileEntity=profileRepository.findAllByEmail( email ).
                orElseThrow(()-> new UsernameNotFoundException( "profile not found for the mail "+email ));
        log.info("Inside loadUserByUsername profile found for the mail "+profileEntity.getEmail() );
        return new User( profileEntity.getEmail(),profileEntity.getPassword(),new ArrayList<>());
    }
}
