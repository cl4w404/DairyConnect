package com.devcorp.DairyConnect.services;

import com.devcorp.DairyConnect.models.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public ResponseEntity<List<Users>> getUsers();
    public ResponseEntity<Users> saveUser(Users user);
    public ResponseEntity<?> searchUser(String uuid);
}
