package com.ecom.repository;

import com.ecom.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByEmailIgnoreCase(String email);

    Optional<AppUser> findByUserName(String userName);

    Optional<AppUser> findByUserNameIgnoreCase(String userName);

    boolean existsByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUserName(String userName);

    boolean existsByUserNameIgnoreCase(String userName);

    Optional<AppUser> findByConfirmationToken(String token);

    Optional<AppUser> findByResetPasswordToken(String token);
}
