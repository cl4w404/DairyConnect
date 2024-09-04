package com.devcorp.DairyConnect;

import com.devcorp.DairyConnect.models.Transactions;
import com.devcorp.DairyConnect.models.UserWithdrawals;
import com.devcorp.DairyConnect.models.Users;
import com.devcorp.DairyConnect.services.TransactionServiceImpl;
import com.devcorp.DairyConnect.services.UserServiceImpl;
import com.devcorp.DairyConnect.services.UserWithdrawlsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/dairyconnect")
public class DairyConnectController {
    @Autowired
    TransactionServiceImpl transactionService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserWithdrawlsServiceImpl userWithdrawlsService;

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(){
        return userService.getUsers();
    }
    @GetMapping("/transactions")
    public ResponseEntity<List<Transactions>> getAllTransactions(){
        return transactionService.getTransactions();
    }
    @GetMapping("/withdrawals")
    public ResponseEntity<List<UserWithdrawals>> getAllWithdrawals(){
        return userWithdrawlsService.getAllWithdrawals();
    }
    @GetMapping("/users/{uuid}")
    public ResponseEntity<String> getSingleUser(@PathVariable String uuid){
        return userService.searchUser(uuid);
    }
    @GetMapping("/transactions/{transactionId}")
    public Optional<Transactions> getSingleTransaction(@PathVariable String transactionId){
        return transactionService.searchTransaction(transactionId);
    }
    @GetMapping("/{uuid}/transactions")
    public List<Transactions> getTransactionWithuuid(@PathVariable String uuid){
        return transactionService.getTransactionWithUuid(uuid);
    }
    @GetMapping("/users/{ref}")
    public ResponseEntity<String> getSingleWithdrawal(@PathVariable String ref){
        return userWithdrawlsService.searchWithdrawal(ref);
    }
    @PostMapping("/users")
    public ResponseEntity<Users> addUser(@RequestBody Users users){
        return userService.saveUser(users);
    }

    @PostMapping("{uuid}/transactions")
    public ResponseEntity<Transactions> addTransactions(@PathVariable String uuid,@RequestBody Transactions transactions){
        return transactionService.saveTransaction(transactions,uuid);
    }
    @PostMapping("{uuid}/withdrawals")
    public ResponseEntity<String> addWithdrawals(@PathVariable String uuid,@RequestBody UserWithdrawals userWithdrawals){
        return userWithdrawlsService.saveWithdrawal(userWithdrawals, uuid);
    }

}
