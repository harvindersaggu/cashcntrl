package com.cashcontrol.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="accounts_summary")
public class AccountsSummary {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    private LocalDate date;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="account_info")
    private OrganisationAccounts accounts;
    
    private Double totalDebit;
    
    private Double totalCredit;
    
    private Double totalWithOpeningBalance;
    
    private Double closingBalance;
    
    private Double openingBalance;
    
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private Scenario scenario;
    
}