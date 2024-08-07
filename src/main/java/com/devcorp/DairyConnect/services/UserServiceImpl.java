package com.devcorp.DairyConnect.services;

import com.devcorp.DairyConnect.models.Users;
import com.devcorp.DairyConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public ResponseEntity<List<Users>> getUsers() {
        return new ResponseEntity<List<Users>>(userRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Users> saveUser(Users user) {
        Optional<Users> users = this.userRepository.findByUuid(user.getUuid());
        if(users.isPresent()){
            return new ResponseEntity<Users>(HttpStatus.BAD_REQUEST);
        }
        {
        return new ResponseEntity<Users>(userRepository.save(user),HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<String> searchUser(String uuid) {
        Optional<Users> user = this.userRepository.findByUuid(uuid);
        if(user.isPresent()){
            userRepository.findByUuid(uuid);
            return new ResponseEntity<>("Users Found", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No User With The Uuid", HttpStatus.NOT_FOUND);
        }
    }
}
