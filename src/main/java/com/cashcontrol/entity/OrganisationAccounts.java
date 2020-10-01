package com.cashcontrol.entity;



import java.util.Date;

 

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
@Table(name="organisation_accounts")
public class OrganisationAccounts {
    
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Id
    @Column(name="account_no")
    private String accountNo;
    
    private String branchLocation;
    
    private Date bankBookFromDate;
    
    private Date bankBookToDate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="organisation_id")
    private Organisation organisation;
   
}