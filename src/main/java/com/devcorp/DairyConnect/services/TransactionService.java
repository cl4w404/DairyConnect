package com.devcorp.DairyConnect.services;

import com.devcorp.DairyConnect.models.Transactions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TransactionService {
      public ResponseEntity<List<Transactions>> getTransactions();
      public ResponseEntity<Transactions> saveTransaction(Transactions transactions,String uuid);
      public ResponseEntity<String> searchTransaction(String transactionId);
}
