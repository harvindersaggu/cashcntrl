package com.cashcontrol.service;

import javax.servlet.http.HttpServletRequest;

import com.cashcontrol.dto.RegistrationRequestDto;
import com.cashcontrol.entity.Users;
import com.cashcontrol.exception.ApplicationException;
import com.cashcontrol.exception.BadRequestException;
import com.cashcontrol.exception.UserEmailPasswordIncorrectException;
import com.cashcontrol.exception.UserNotFoundException;
import com.cashcontrol.model.PostSignInResponse;

public interface AuthenticationService {
	Users doRegistration(RegistrationRequestDto requestDto, HttpServletRequest httpRequest) throws ApplicationException;

	//boolean doEmailActivation(String token) throws TokenException, ApplicationException;

	PostSignInResponse login(String email, String password)
			throws UserNotFoundException, UserEmailPasswordIncorrectException, ApplicationException;

	boolean doLogout(String token) throws BadRequestException, ApplicationException;

	/*
	 * Users resendVerificationLink(String email, HttpServletRequest
	 * httpServletRequest) throws UsernameNotFoundException, ApplicationException;
	 * 
	 * boolean resetPassword(String password, String token) throws TokenException,
	 * ApplicationException;
	 * 
	 * boolean checkResetPasswordToken(String token) throws TokenException,
	 * ApplicationException;
	 * 
	 * boolean forgotPassword(String email, HttpServletRequest httpServletRequest)
	 * throws BadRequestException, ApplicationException;
	 * 
	 * String doNewEmailActivation(String token) throws TokenException,
	 * ApplicationException;
	 * 
	 * boolean deactivateAccount(String token, HttpServletRequest httpRequest)
	 * throws Exception;
	 * 
	 * String generateResetPwdAuthToken(String token) throws ApplicationException;
	 * 
	 * String generateResendVerificationToken(String expiredToken,
	 * HttpServletRequest httpServletRequest) throws ApplicationException;
	 * 
	 * String generateResendResetPasswordToken(String expiredToken,
	 * HttpServletRequest httpServletRequest) throws ApplicationException;
	 */

}
