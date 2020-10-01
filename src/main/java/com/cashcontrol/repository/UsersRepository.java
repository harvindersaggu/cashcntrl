package com.cashcontrol.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cashcontrol.entity.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

	Optional<Users> findByUsername(String username);
	
	Optional<Users> findByEmail(String email);
	
	Optional<Users> findByUserid(Long id);

	@Query("from Users where userid=(select userid from UserToken where token=:token)")
	Optional<Users> findUserByToken(String token);

	@Query("select isEmailVerified from Users u where u.email=:mail")
	boolean isEmailVerified(String mail);

	@Query("select u.isActive from Users u where u.email=:mail")
	boolean isUserActive(String mail);

	@Query("select u.isEmailUpdateInprogress from Users u where u.email=:userEmail")
	boolean isUpdateInprogress(String userEmail);
}
