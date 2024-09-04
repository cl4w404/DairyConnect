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
            double amount = user.getBalance();
            double earnings = transactions.getLiters()*40;
            double addedAmount = amount + earnings;
            user.setBalance(addedAmount);
            transactions.setUsers(user);
        return new ResponseEntity<Transactions>(transactionRepository.save(transactions),HttpStatus.CREATED);
        }
    }

    @Override

    public Optional<Transactions> searchTransaction(String transactionId) {
        Optional<Transactions> transactions1 = this.transactionRepository.findByTransactionId(transactionId);
        if(transactions1.isPresent()){
            Transactions transaction = transactions1.get();

          return  transactionRepository.findByTransactionId(transactionId);
        }else{
            return  null;
        }
    }
    public List<Transactions> getTransactionWithUuid(String uuid){
        Optional<Users> user = this.userRepository.findByUuid(uuid);
        if (user.isPresent()){
            Users users = user.get();
            return users.getTransactions();
        }else {
            return null;
        }
    }
    public ResponseEntity<Optional<Transactions>> deleteTransaction(long id){
        Optional<Transactions> transactions = transactionRepository.findById(id);
        if(transactions.isPresent()){
            return new ResponseEntity<Optional<Transactions>>(transactionRepository.findById(id),HttpStatus.OK);
        }else {
            return null;
        }


    }
}
