package com.github.holiver98.dal.jpa;

import com.github.holiver98.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailAddress(String emailAddress);
}
