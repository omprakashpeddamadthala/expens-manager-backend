package com.expens.manager.controller;

import com.expens.manager.dto.ProfileDTO;
import com.expens.manager.io.AuthRequest;
import com.expens.manager.io.AuthResponse;
import com.expens.manager.io.ProfileRequest;
import com.expens.manager.io.ProfileResponse;
import com.expens.manager.service.CustomUserDetailsService;
import com.expens.manager.service.ProfileService;
import com.expens.manager.service.impl.TokenBlackListService;
import com.expens.manager.utils.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is responsible for handling the authentication related requests
 * @author Om Prakash Peddamadthala
 * @version 1.0
 * @since 2024-12-29
 * */
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final ModelMapper modelMapper;
    private final ProfileService profileService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final TokenBlackListService tokenBlackListService;

    /**
     * This method is responsible for creating a new profile
     * @param profileRequest
     * @return ProfileResponse
     * */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ProfileResponse ProfileRequest(@Valid  @RequestBody ProfileRequest profileRequest){
        log.info( "API /register called profileRequest {}",profileRequest );
        ProfileDTO profileDTO=mapToProfileDTO(profileRequest);
        profileDTO =profileService.createProfile( profileDTO );
        log.info( "Printing profile details profileDTO {}", profileDTO );
        return mapToProfileResponse( profileDTO );
    }
    /**
     * This method is responsible for authenticating the user
     * @param authRequest
     * @return AuthResponse
     * */
    @PostMapping("/login")
    public AuthResponse authenticateProfile(@RequestBody AuthRequest authRequest) throws Exception {
        log.info( "API /login called authRequest {}",authRequest );
        authenticate( authRequest );
        final UserDetails userDetails =userDetailsService.loadUserByUsername(authRequest.getEmail()  );
        final String token = jwtTokenUtil.generateToken( userDetails );
        return new AuthResponse( token, authRequest.getEmail() );
    }


    /**
     * This method is used to logout the user
     * @param httpServletRequest
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/signout")
    public void logout(HttpServletRequest httpServletRequest){
        log.info( "API /logout called ");
        String token = extractTokenFromRequest(httpServletRequest);
        if(!StringUtils.isEmpty( token )) {
            tokenBlackListService.addTokenToBlackList( token );
        }
    }

    /**
     * This method is used to extract the token from the httpServletRequest
     * @param httpServletRequest
     * @return null
     */
    private String extractTokenFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader( "Authorization");
        if(bearerToken!=null && bearerToken.startsWith( "Bearer " )) {
            return bearerToken.substring( 7 );
        }
        return null;
    }

    /**
     * This method is used to authenticate the user
     * @param authRequest
     * @throws Exception
     */
    private void authenticate(AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( authRequest.getEmail(), authRequest.getPassword() ) );
        }catch (DisabledException exception){
             throw  new Exception("Profile Disabled");
        }catch (BadCredentialsException exception){
            throw  new Exception("Bad Credentials ");
        }
        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken( authRequest.getEmail(), authRequest.getPassword() ) );
    }


    /**
     * This method is used to map the ProfileRequest to ProfileDTO
     * @param profileRequest
     * @return profileDTO
     * */
    private ProfileDTO mapToProfileDTO(ProfileRequest profileRequest) {
        return modelMapper.map( profileRequest,ProfileDTO.class );
    }

    /**
     * This method is used to map the ProfileDTO to ProfileResponse
     * @param profileDTO
     * @return profileResponse
     * */
    private ProfileResponse mapToProfileResponse(ProfileDTO profileDTO) {
        return modelMapper.map( profileDTO,ProfileResponse.class );
    }
}
