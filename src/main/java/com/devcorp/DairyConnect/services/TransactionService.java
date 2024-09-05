package com.devcorp.DairyConnect.services;

import com.devcorp.DairyConnect.models.Transactions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TransactionService {
      public ResponseEntity<List<Transactions>> getTransactions();
      public ResponseEntity<String> deleteTransaction(long id);
      public List<Transactions> getTransactionWithUuid(String uuid);
      public ResponseEntity<Transactions> saveTransaction(Transactions transactions, String uuid);
      public Optional<Transactions> searchTransaction(String transactionId);
}
