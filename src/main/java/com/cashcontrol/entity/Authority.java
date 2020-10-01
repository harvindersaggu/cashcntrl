package com.cashcontrol.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "authorities")
@Data
@ToString
public class Authority implements Serializable {

	private static final long serialVersionUID = 7758761816659731714L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "roleid", updatable = false, nullable = false)
	private long roleId;

	private String authority;

	@ManyToOne
	@JoinColumn(name="userid") 
	private Users user;

}
