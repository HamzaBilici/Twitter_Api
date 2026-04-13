package com.hbilici.twitter_api.repository;

import com.hbilici.twitter_api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByAuthority(String authority);
}
