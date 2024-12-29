package com.expens.manager.repository;

import com.expens.manager.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity,Long> {

    Boolean existsByEmail(String email);
}
