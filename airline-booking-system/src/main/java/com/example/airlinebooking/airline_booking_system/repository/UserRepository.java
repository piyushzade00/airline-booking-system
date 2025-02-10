package com.example.airlinebooking.airline_booking_system.repository;

import com.example.airlinebooking.airline_booking_system.entity.UserEntity;
import com.example.airlinebooking.airline_booking_system.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity saveUserEntity(UserEntity userEntity);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(UserRoles role);

    List<UserEntity> findAllUsers();

    boolean deleteByUserName(String userName);

    boolean deleteByEmail(String email);

}
