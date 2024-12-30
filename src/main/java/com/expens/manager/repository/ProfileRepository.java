package com.expens.manager.repository;

import com.expens.manager.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {

    Boolean existsByEmail(String email);

    Optional<ProfileEntity> findAllByEmail(String email);
}
