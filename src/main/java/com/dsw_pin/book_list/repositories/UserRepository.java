package com.dsw_pin.book_list.repositories;

import com.dsw_pin.book_list.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);

}
