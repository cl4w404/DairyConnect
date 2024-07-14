package com.devcorp.DairyConnect.services;

import com.devcorp.DairyConnect.models.UserWithdrawals;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserWithdrawlsService {
    public ResponseEntity<List<UserWithdrawals>> getAllWithdrawals();
    public ResponseEntity<String> saveWithdrawal(UserWithdrawals userWithdrawals, String uuid);
    public ResponseEntity<String> searchWithdrawal(String ref);
}
