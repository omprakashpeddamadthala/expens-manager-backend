package com.expens.manager.service.impl;


import com.expens.manager.dto.ProfileDTO;
import com.expens.manager.entity.ProfileEntity;
import com.expens.manager.expection.ItemExistsException;
import com.expens.manager.repository.ProfileRepository;
import com.expens.manager.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * This service class is used to create a new profile and
 * profile related operations
 * */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ModelMapper modelMapper;
    private final ProfileRepository profileRepository;

    /**
     * This method is used to create a new profile
     * @param profileDTO
     * @return profileDTO
     * */
    @Override
    public ProfileDTO createProfile(ProfileDTO profileDTO) {
        if (profileRepository.existsByEmail(profileDTO.getEmail())) {
            throw new ItemExistsException("Profile already exists "+profileDTO.getEmail());
        }
        ProfileEntity profileEntity =mapToProfileEntity(profileDTO);
        profileEntity.setProfileId( UUID.randomUUID().toString() );
        ProfileEntity savedProfileEntity =profileRepository.save( profileEntity );
        return mapToProfileDTO(savedProfileEntity);
    }

    /**
     * This method is used to map to from ProfileEntity to ProfileDTO
     * @param savedProfileEntity
     * @return profileDTO
     * */
    private ProfileDTO mapToProfileDTO(ProfileEntity savedProfileEntity) {
        return modelMapper.map(savedProfileEntity,ProfileDTO.class);
    }

    /**
     * This method is used to map to from ProfileDTO to ProfileEntity
     * @param profileDTO
     * @return profileEntity
     * */
    private ProfileEntity mapToProfileEntity(ProfileDTO profileDTO) {
        return modelMapper.map( profileDTO,ProfileEntity.class );
    }
}
