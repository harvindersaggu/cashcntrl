package com.cashcontrol.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cashcontrol.enums.TokenType;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "token")
@Data
@ToString
public class UserToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private long id;

	@Column(name = "token")
	private String token;

	@Transient
	private Users user;

	@Column(name = "userid")
	private long userid;

	@Column(name = "token_type")
	private int tokenType;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "created_on")
	private Date createdOn;

	public UserToken() {
	}

	public UserToken(Users user, TokenType tokenType) {
		this.user = user;
		this.userid = user.getUserid();
		this.createdOn = new Date();
		this.token = UUID.randomUUID().toString();
		this.tokenType = tokenType.getTokenTypeId();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public int getTokenType() {
		return tokenType;
	}

	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userId) {
		this.userid = userId;
	}
}
