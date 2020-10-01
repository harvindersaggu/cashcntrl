package com.cashcontrol.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cashcontrol.constant.ErrorConstant;
import com.cashcontrol.entity.Users;
import com.cashcontrol.enums.CustomErrorCode;
import com.cashcontrol.exception.UserEmailPasswordIncorrectException;
import com.cashcontrol.exception.UserNotFoundException;
import com.cashcontrol.model.PostSignInResponse;
import com.cashcontrol.model.RegistrationRequest;
import com.cashcontrol.model.SignInRequest;
import com.cashcontrol.model.SignUpResponse;
import com.cashcontrol.payload.ApiResponse;
import com.cashcontrol.service.AuthenticationService;
import com.cashcontrol.service.UsersService;
import com.cashcontrol.util.AuthenicationValidationUtil;
import com.cashcontrol.util.FieldError;
import com.cashcontrol.util.RegistrationRequestConverter;
import com.cashcontrol.util.ValidationUtil;

@RestController
public class AuthenicationController {

	private Logger logger = LoggerFactory.getLogger(AuthenicationController.class);

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private RegistrationRequestConverter registrationRequestConverter;

	@Autowired
	private UsersService userService;

	@Autowired
	private ValidationUtil validationUtil;

	AuthenicationController() {
		logger.debug("{} object created", this.getClass().getSimpleName());
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> doRegistration(@RequestBody RegistrationRequest registrationRequest,
			HttpServletRequest httpRequest) throws ApplicationException {
		logger.debug("{} doRegistration method started", this.getClass().getSimpleName());

		List<FieldError> errors = AuthenicationValidationUtil.applyAll(registrationRequest);
		if (userService.isUserNamePresent(registrationRequest.getUsername())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(Boolean.FALSE, ErrorConstant.USERNAME_ALREADY_EXIST,
							"Username is " + "already registered", CustomErrorCode.USERNAME_ALREADY_EXIST,
							HttpStatus.OK));
		}
		Users user = null;
		if (errors.isEmpty()) {
			user = authenticationService.doRegistration(registrationRequestConverter.convertToDto(registrationRequest),
					httpRequest);

		} else {
			String b = errors.stream().map(FieldError::getFieldMessage).collect(Collectors.joining(", "));
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse(Boolean.TRUE, b, null, null, HttpStatus.OK));
		}
		SignUpResponse signUpResponse = new SignUpResponse(user.getUsername(), user.getEmail(), user.isEmailVerified());
		logger.debug("{} doRegistration method ended", this.getClass().getSimpleName());
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(Boolean.TRUE,
				"Verification link has been sent to " + "the email-id", signUpResponse, null, HttpStatus.OK));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> doLogin(@RequestBody SignInRequest authenticationRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse response) throws Exception {
		logger.info("{} doLogin method started ", this.getClass().getSimpleName());
		try {
			if (!validationUtil.isObjectPresent(authenticationRequest)) {
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(Boolean.FALSE, "Empty params", CustomErrorCode.EMPTY_PARAMS,
						HttpStatus.NOT_FOUND));
			}
			if (!validationUtil.isStringEmpty(authenticationRequest.getPassword())) {
				return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(Boolean.FALSE, "Password is Empty",
						CustomErrorCode.EMPTY_PASSWORD, HttpStatus.NOT_FOUND));
			}

			PostSignInResponse postSignInResponse = authenticationService.login(authenticationRequest.getUsername(),
					authenticationRequest.getPassword());
			if (postSignInResponse.isUserEnabled()) {

				response.addHeader("authToken", postSignInResponse.getAuthToken().getAccessToken());
				response.addHeader("refresh-token", postSignInResponse.getAuthToken().getRefreshToken());
				return ResponseEntity
						.ok(new ApiResponse(Boolean.TRUE, "Valid user credentials", postSignInResponse, HttpStatus.OK));
			} else {
				logger.info("{} doLogin method ended ", this.getClass().getSimpleName());
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApiResponse(Boolean.FALSE, ErrorConstant.REGISTERED_BUT_EMAIL_NOT_VERIFIED,
								postSignInResponse, CustomErrorCode.REGISTERED_BUT_EMAIL_NOT_VERIFIED, HttpStatus.OK));
			}

		} catch (UserEmailPasswordIncorrectException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(Boolean.FALSE,
					"Incorrect " + "credentials", CustomErrorCode.INVALID_USER_CREDENTIALS, HttpStatus.UNAUTHORIZED));
		} catch (UserNotFoundException e) {
			logger.error(e.getMessage());
			throw new UserNotFoundException("email not found");
		}
	}

}
