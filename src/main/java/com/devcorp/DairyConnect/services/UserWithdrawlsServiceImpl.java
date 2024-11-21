package com.devcorp.DairyConnect.services;

import com.devcorp.DairyConnect.models.UserWithdrawals;
import com.devcorp.DairyConnect.models.Users;
import com.devcorp.DairyConnect.repository.UserRepository;
import com.devcorp.DairyConnect.repository.UserWithdrawlsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserWithdrawlsServiceImpl implements UserWithdrawlsService{
    @Autowired
    UserWithdrawlsRepository userWithdrawlsRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public ResponseEntity<List<UserWithdrawals>> getAllWithdrawals() {
        return new ResponseEntity<List<UserWithdrawals>>(userWithdrawlsRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> saveWithdrawal(UserWithdrawals userWithdrawals, String uuid) {
        Optional<UserWithdrawals> userWithdrawals1 = this.userWithdrawlsRepository.findByRef(userWithdrawals.getRef());

        if(userWithdrawals1.isPresent()){
           return new ResponseEntity<String>("User Withdrawal Request is present",HttpStatus.BAD_REQUEST);
        }else{
            Optional<Users> user = this.userRepository.findByUuid(uuid);
            if (user.isPresent()) {
                Users use = user.get();
                double currentBalance = use.getBalance();
                double withdrawAmount = userWithdrawals.getAmount();
                if(currentBalance >= withdrawAmount){
                    use.setBalance(currentBalance - withdrawAmount);
                    userWithdrawals.setUsers(use);
                    userWithdrawlsRepository.save(userWithdrawals);
                    return new ResponseEntity<String>("Succesfull Withdrawing",HttpStatus.CREATED);
                }else {
                    return new ResponseEntity<String>("You Have insufficient Balance", HttpStatus.BAD_REQUEST);
                }
            }else{
               return new ResponseEntity<String>("No such User",HttpStatus.NOT_FOUND);
            }
        }
    }

    @Override
    public ResponseEntity<String> searchWithdrawal(String ref) {
        Optional<UserWithdrawals> userWithdrawals = this.userWithdrawlsRepository.findByRef(ref);
        if(userWithdrawals.isPresent()){
            userWithdrawlsRepository.findByRef(ref);
            return new ResponseEntity<>("Withdrawal Found", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Withdrawal Not Found", HttpStatus.NOT_FOUND);
        }
    }
    @Override
    public ResponseEntity<String> deleteWitdrawal(long id) {
        Optional<UserWithdrawals> transaction = this.userWithdrawlsRepository.findById(id);
        if (transaction.isPresent()) {
            userWithdrawlsRepository.deleteById(id);
            return new ResponseEntity<>("Withdrawal deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Withdrawal not found", HttpStatus.NOT_FOUND);
        }
    }
}
