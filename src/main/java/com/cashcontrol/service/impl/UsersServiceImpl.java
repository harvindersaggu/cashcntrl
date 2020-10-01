package com.cashcontrol.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cashcontrol.entity.Users;
import com.cashcontrol.exception.ApplicationException;
import com.cashcontrol.exception.TokenException;
import com.cashcontrol.repository.UsersRepository;
import com.cashcontrol.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

	Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public boolean isUserEmailPresent(String userEmail) throws ApplicationException {
		logger.debug("{} isUserEmailPresent method started", this.getClass().getSimpleName());
		try {
			Optional<Users> usersOptional = fetchUserByEmail(userEmail);
			if (usersOptional.isPresent()) {
				logger.debug("{} isUserEmailPresent method ended",this.getClass().getSimpleName());
				return Boolean.TRUE;
			}
		} catch (ApplicationException e) {
			logger.error("{} error finding the email:", e.getMessage());
			throw e;
		}
		return false;
	}

	@Override
	public Optional<Users> fetchUserById(long id) throws ApplicationException {
		try {
			return usersRepository.findByUserid(id);
		} catch (Exception e) {
			logger.error("{} error fetching the user:", e.getMessage());
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Users> fetchUserByEmail(String email) throws ApplicationException {
		logger.debug("{} fetchUserByEmail method started",this.getClass().getSimpleName() );
		try {
			logger.debug("{} fetchUserByEmail method ended",this.getClass().getSimpleName());
			return usersRepository.findByEmail(email);

		} catch (Exception e) {
			logger.error("{} error finding the email:", e.getMessage());
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	@Override
	public Optional<Users> fetchUserByToken(String token) throws ApplicationException {
		logger.debug("{} fetchUserByToken method started ,{} token",this.getClass().getSimpleName(),token);
		try {
			logger.debug("{} fetchUserByToken method ended",this.getClass().getSimpleName() );
			return usersRepository.findUserByToken(token);

		} catch (Exception e) {
			logger.error("{} error finding the user:", e.getMessage());
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	@Override
	public boolean isUserEmailVerified(String userEmail) throws Exception {
		logger.debug(this.getClass().getSimpleName() + " isUserEmailVerified method started");
		try {
			logger.debug(this.getClass().getSimpleName() + " isUserEmailVerified method ended");
			return usersRepository.isEmailVerified(userEmail);
		} catch (Exception e) {
			logger.error("error finding the email:", e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean checkIfPasswordIsSame(String newPassword, String token) throws TokenException, ApplicationException {
		try {
			Optional<Users> usersOptional = usersRepository.findUserByToken(token);
			if (usersOptional.isPresent()) {
				String oldPassword = usersOptional.get().getPassword();
				return passwordEncoder.matches(newPassword, oldPassword);
			} else {
				throw new TokenException("No token available");
			}
		} catch (TokenException e) {
			throw e;
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage(), e);
		}
	}

	@Override
	public boolean isUserActive(String userEmail) throws Exception {
		logger.debug(this.getClass().getSimpleName() + " isUserActive method started");
		try {
			logger.debug(this.getClass().getSimpleName() + " isUserActive method ended");
			return usersRepository.isUserActive(userEmail);
		} catch (Exception e) {
			logger.error("error finding the email:", e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean isUpdateInProgress(String userEmail) throws Exception {
		logger.debug(this.getClass().getSimpleName() + " isUpdateInProgress method started");
		try {
			logger.debug(this.getClass().getSimpleName() + " isUpdateInProgress method ended");
			return usersRepository.isUpdateInprogress(userEmail);
		} catch (Exception e) {
			logger.error("error previous email update in progress:", e.getMessage());
			throw e;
		}
	}

	@Override
	public boolean isUserNamePresent(String userEmail) throws ApplicationException {
		try {
			Optional<Users> usersOptional = usersRepository.findByUsername(userEmail);
			if (usersOptional.isPresent()) {
				logger.debug(this.getClass().getSimpleName() + " isUserEmailPresent method ended");
				return Boolean.TRUE;
			}
		} catch (ApplicationException e) {
			logger.error("error finding the email:", e.getMessage());
			throw e;
		}
		return false;
	}

}
