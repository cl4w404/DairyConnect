package com.devcorp.DairyConnect.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String uuid;
    private Date date = new Date(System.currentTimeMillis());
    private double balance;
    private boolean active;
    @OneToMany(mappedBy = "users")
    private List<Transactions> transactions;
    @OneToMany(mappedBy = "users")
    private List<UserWithdrawals> withdrawals;
}
