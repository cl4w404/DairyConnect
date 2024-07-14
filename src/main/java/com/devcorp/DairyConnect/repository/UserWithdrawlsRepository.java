package com.devcorp.DairyConnect.repository;

import com.devcorp.DairyConnect.models.UserWithdrawals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserWithdrawlsRepository extends JpaRepository<UserWithdrawals, Long> {
    Optional<UserWithdrawals> findByRef(String ref);
}
