package com.cashcontrol.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cashcontrol.dto.RegistrationRequestDto;
import com.cashcontrol.dto.TokenPair;
import com.cashcontrol.entity.Authority;
import com.cashcontrol.entity.UserToken;
import com.cashcontrol.entity.Users;
import com.cashcontrol.enums.TokenType;
import com.cashcontrol.exception.ApplicationException;
import com.cashcontrol.exception.UserEmailPasswordIncorrectException;
import com.cashcontrol.exception.UserNotFoundException;
import com.cashcontrol.model.PostSignInResponse;
import com.cashcontrol.repository.UserRolesRepository;
import com.cashcontrol.repository.UserTokenRepository;
import com.cashcontrol.repository.UsersRepository;
import com.cashcontrol.service.AuthenticationService;
import com.cashcontrol.util.TokenUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	Environment environment;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private UserRolesRepository userRolesRepository;

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Override
	public Users doRegistration(RegistrationRequestDto requestDto, HttpServletRequest httpRequest)
			throws ApplicationException {
		logger.debug(this.getClass().getSimpleName() + " doRegistration method started");
		Users user = new Users();
		try {
			
			user.setUsername(requestDto.getUsername());
			user.setEmail(requestDto.getEmail());
			// encrypting password
			user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
			user.setActive(Boolean.TRUE);
			user.setEmailVerified(Boolean.FALSE);
			user = usersRepository.save(user);

			List<Authority> list = new ArrayList<>(requestDto.getAuthorities());
			for (Authority authority : list) {
				authority.setUser(user);
				userRolesRepository.save(authority);
				UserToken userToken = new UserToken(user, TokenType.VERIFICATION_TOKEN);
				userTokenRepository.save(userToken);
				logger.debug(this.getClass().getSimpleName() + " doRegistration method ended");
				// sendVerificationEmail(user, httpRequest, userToken.getToken());
			}
		} catch (Exception e) {
			logger.error("error:", e.getMessage());
			throw new ApplicationException(e.getMessage(), e);
		}

		return user;
	}

	@Override
	public PostSignInResponse login(String username, String password)
			throws UserEmailPasswordIncorrectException, UserNotFoundException, ApplicationException {
		logger.debug(this.getClass().getSimpleName() + " login method started");
		try {
			Optional<Users> usersOptional = usersRepository.findByUsername(username);
			if (!usersOptional.isPresent()) {
				throw new UserNotFoundException("incorrect email");
			}
			if (usersOptional.get().getUsername().equalsIgnoreCase(username)
					&& passwordEncoder.matches(password, usersOptional.get().getPassword())) {
			} else {
				throw new UserEmailPasswordIncorrectException("incorrect credentials");
			}

			if (usersOptional.get().isEmailVerified()) {
				usersOptional.get().setActive(Boolean.TRUE);
				usersRepository.save(usersOptional.get());
				final TokenPair tokenPair = tokenUtil.createRefreshAndAccessToken(usersOptional.get());
				PostSignInResponse postSignInResponse = new PostSignInResponse();
				postSignInResponse.setAuthToken(tokenPair);
				postSignInResponse.setUserId(usersOptional.get().getUserid());
				postSignInResponse.setUserEnabled(Boolean.TRUE);
				postSignInResponse.setUseremail(usersOptional.get().getEmail());
				postSignInResponse.setUsername(usersOptional.get().getUsername());

				postSignInResponse.setAuthToken(tokenPair);
				postSignInResponse.setUserEnabled(Boolean.TRUE);

				logger.debug(this.getClass().getSimpleName() + " login method ended");
				return postSignInResponse;
			} else {
				PostSignInResponse postSignInResponse = new PostSignInResponse();
				postSignInResponse.setUseremail(usersOptional.get().getEmail());
				postSignInResponse.setUsername(usersOptional.get().getUsername());

				postSignInResponse.setUserEnabled(Boolean.FALSE);
				logger.debug(this.getClass().getSimpleName() + " login method ended");
				return postSignInResponse;
			}
		} catch (UserEmailPasswordIncorrectException e) {
			logger.error("Incorrect details", e.getMessage());
			throw e;
		} catch (UserNotFoundException e) {
			logger.error("User not found", e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("error occurred", e);
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public boolean doLogout(String token) throws ApplicationException {
		logger.debug(this.getClass().getSimpleName() + " doLogout method started");
		try {
			Optional<UserToken> optionalUserToken = userTokenRepository.findByToken(token);
			if (optionalUserToken.isPresent()) {
				userTokenRepository.deleteByToken(optionalUserToken.get().getToken());
				List<UserToken> optionalRefreshUserToken = userTokenRepository.findAllByuseridAndTokenType(
						optionalUserToken.get().getUserid(), TokenType.REFRESH_TOKEN.getTokenTypeId());

				userTokenRepository.deleteAll(optionalRefreshUserToken);
				List<UserToken> optionalAuthUserToken = userTokenRepository.findAllByuseridAndTokenType(
						optionalUserToken.get().getUserid(), TokenType.AUTHORIZATION_TOKEN.getTokenTypeId());

				userTokenRepository.deleteAll(optionalAuthUserToken);
				logger.debug(this.getClass().getSimpleName() + " doLogout method ended");
				return true;
			}
		} catch (Exception e) {
			logger.error("error during logout", e.getMessage());
			throw new ApplicationException(e.getMessage(), e);
		}
		return false;
	}

}
