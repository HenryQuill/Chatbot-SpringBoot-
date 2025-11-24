package com.example.chatbotv2.repositories;


import java.util.Optional;
import com.example.chatbotv2.models.Role;
import com.example.chatbotv2.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
