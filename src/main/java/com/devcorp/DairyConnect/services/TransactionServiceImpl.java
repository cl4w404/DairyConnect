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
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<List<Transactions>> getTransactions() {
        return new ResponseEntity<>(transactionRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Transactions> saveTransaction(Transactions transactions, String uuid) {
        Optional<Transactions> transactions1 = this.transactionRepository.findByTransactionId(transactions.getTransactionId());
        Optional<Users> users = this.userRepository.findByUuid(uuid);

        if (transactions1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Transaction already exists
        } else {
            if (users.isPresent()) {
                Users user = users.get();
                double amount = user.getBalance();
                double earnings = transactions.getLiters() * 40; // Assuming 40 is the rate per liter
                double addedAmount = amount + earnings;
                user.setBalance(addedAmount);
                transactions.setUsers(user);

                // Save the transaction
                transactionRepository.save(transactions);

                // After saving, check for failed transactions (status == false)
                List<Transactions> userTransactions = user.getTransactions();
                long failedCount = userTransactions.stream()
                        .filter(t -> !t.isStatus()) // false status (failed transactions)
                        .count();

                // If failed transactions are more than 3, delete all transactions and deactivate user
                if (failedCount >= 3) {
                    deleteAllTransactionsForUser(uuid); // Deletes all transactions and deactivates the user
                    return new ResponseEntity<>(HttpStatus.LOCKED); // Return Locked status as user is deactivated
                }

                // Return success
                return new ResponseEntity<>(transactions, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
            }
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

    public List<Transactions> getTransactionWithUuid(String uuid) {
        Optional<Users> user = this.userRepository.findByUuid(uuid);
        return user.map(Users::getTransactions).orElse(null);
    }

    @Override
    public ResponseEntity<String> deleteTransaction(long id) {
        Optional<Transactions> transaction = this.transactionRepository.findById(id);
        if (transaction.isPresent()) {
            transactionRepository.deleteById(id);
            return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Transaction not found", HttpStatus.NOT_FOUND);
        }
    }

    // Method to delete all transactions and deactivate user
    @Override
    public void deleteAllTransactionsForUser(String uuid) {
        Optional<Users> user = this.userRepository.findByUuid(uuid);

        if (user.isPresent()) {
            Users foundUser = user.get();

            // Delete all transactions associated with the user
            List<Transactions> transactions = foundUser.getTransactions();
            transactions.forEach(transaction -> transactionRepository.deleteById(transaction.getId()));

            // Deactivate the user (assuming there's a status flag to mark deactivation)
            foundUser.setActive(false); // Assuming there's a field "active" to indicate if the user is active
            userRepository.save(foundUser);

            new ResponseEntity<>("User deactivated and all transactions deleted successfully", HttpStatus.OK);
        } else {
            new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
