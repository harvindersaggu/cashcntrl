package com.cashcontrol.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@ToString
public class Users implements Serializable {

	private static final long serialVersionUID = -1859012158521160415L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userid", unique = true, nullable = false)
	private long userid;

	private String username;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Authority> roles;

	private String password;

	private boolean enabled;

	private String email;

	private boolean isActive;

	private boolean isEmailVerified;

	private boolean isEmailUpdateInprogress;

	public Users(Users user) {

		this.username = user.getUsername();
		this.password = user.getPassword();
		this.enabled = user.isEnabled();
	}

	public Users() {
		super();
	}

}
