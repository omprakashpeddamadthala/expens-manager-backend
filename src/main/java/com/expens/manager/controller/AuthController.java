package com.expens.manager.controller;

import com.expens.manager.dto.ProfileDTO;
import com.expens.manager.io.ProfileRequest;
import com.expens.manager.io.ProfileResponse;
import com.expens.manager.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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
