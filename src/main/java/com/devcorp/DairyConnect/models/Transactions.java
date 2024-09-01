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
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String transactionId;
    private double liters;
    private boolean status;
    private Date date = new Date(System.currentTimeMillis());
    @ManyToOne
    @JsonIgnore
    private Users users;
}
