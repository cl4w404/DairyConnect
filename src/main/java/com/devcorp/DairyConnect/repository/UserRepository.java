package com.devcorp.DairyConnect.repository;

import com.devcorp.DairyConnect.models.Transactions;
import com.devcorp.DairyConnect.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUuid(String uuid);
}
