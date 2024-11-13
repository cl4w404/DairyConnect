package com.devcorp.DairyConnect.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class UserWithdrawals {
    @Id
    @GeneratedValue
    private long id;
    private double amount;
    private Date date = new Date(System.currentTimeMillis());
    private String ref;
    private String type;
    @ManyToOne
    @JsonIgnore
    private Users users;
}
