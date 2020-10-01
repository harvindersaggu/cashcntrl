package com.cashcontrol.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cashcontrol.entity.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String> {

	Optional<UserToken> findByToken(String token);

	Optional<UserToken> findByTokenAndTokenType(String token, int tokenType);

	@Transactional
	@Modifying
	void deleteByuserid(long userid);

	@Transactional
	@Modifying
	@Query("delete from UserToken as ut where ut.userid=:userid and tokenType=:tokenType")
	void deleteByuseridAndTokenType(long userid, int tokenType);

	@Transactional
	@Modifying
	@Query("delete from UserToken as ut where ut.userid in (select userid from Users where userEmail=:email) and tokenType=:tokenType")
	void deleteByUserEmailAndTokenType(String email, int tokenType);

	@Transactional
	@Modifying
	@Query("delete from UserToken as ut where token=:token")
	void deleteByToken(String token);

	@Query("from UserToken as ut where ut.userid=ut.userid and ut.tokenType = :tokenType" + " and ut.userid=:userid")
	List<UserToken> findAllByuseridAndTokenType(long userid, int tokenType);

	@Query("from UserToken as ut where ut.userid=(select userid from Users where "
			+ "userEmail=:userEmail) and ut.tokenType " + "= :tokenType")
	Optional<UserToken> findByUserEmailAndTokenType(String userEmail, int tokenType);

}
