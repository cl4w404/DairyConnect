package com.devcorp.DairyConnect.services;

import com.devcorp.DairyConnect.models.Transactions;
import com.devcorp.DairyConnect.models.Users;
import com.devcorp.DairyConnect.repository.TransactionRepository;
import com.devcorp.DairyConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public ResponseEntity<List<Transactions>> getTransactions() {
        return new ResponseEntity<List<Transactions>>(transactionRepository.findAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Transactions> saveTransaction(Transactions transactions,String uuid) {
        Optional<Transactions> transactions1 = this.transactionRepository.findByTransactionId(transactions.getTransactionId());
        Optional<Users> users = this.userRepository.findByUuid(uuid);
        if(transactions1.isPresent()){
            return new ResponseEntity<Transactions>(HttpStatus.BAD_REQUEST);
        }else {
            Users user = users.get();
            transactions.setUsers(user);
        return new ResponseEntity<Transactions>(transactionRepository.save(transactions),HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<String> searchTransaction(String transactionId) {
        Optional<Transactions> transactions1 = this.transactionRepository.findByTransactionId(transactionId);
        if(transactions1.isPresent()){
            Transactions transaction = transactions1.get();
            transactionRepository.findByTransactionId(transactionId);
          return  new ResponseEntity<>("Succesfully Retrived", HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("No Transaction With The Id",HttpStatus.NOT_FOUND);
        }
    }
}
