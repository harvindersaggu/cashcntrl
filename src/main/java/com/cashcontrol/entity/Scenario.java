package com.cashcontrol.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

 

import lombok.Data;

 

@Data
@Entity
@Table(name = "scenario")
public class Scenario {

 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

 

    @Column(name = "name")
    private String name;
    
    @Column(name = "is_deleted")
    private Boolean isDeleted;
}