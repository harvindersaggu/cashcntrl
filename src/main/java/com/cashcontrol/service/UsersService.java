package com.cashcontrol.service;

import java.util.Optional;

import com.cashcontrol.entity.Users;
import com.cashcontrol.exception.ApplicationException;
import com.cashcontrol.exception.TokenException;

public interface UsersService {

	boolean isUserEmailPresent(String userEmail) throws ApplicationException;
	
	boolean isUserNamePresent(String userEmail) throws ApplicationException;

	Optional<Users> fetchUserById(long id) throws ApplicationException;

	Optional<Users> fetchUserByEmail(String email) throws ApplicationException;

	Optional<Users> fetchUserByToken(String token) throws ApplicationException;

	boolean checkIfPasswordIsSame(String newPassword, String token) throws TokenException, ApplicationException;

	boolean isUserEmailVerified(String userEmail) throws Exception;

	boolean isUserActive(String userEmail) throws Exception;

	boolean isUpdateInProgress(String username) throws Exception;
}
