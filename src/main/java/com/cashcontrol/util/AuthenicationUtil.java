package com.cashcontrol.util;

import java.util.function.Function;
import java.util.function.Predicate;

import com.cashcontrol.model.RegistrationRequest;

public interface AuthenicationUtil extends Function<RegistrationRequest, FieldError> {

	static AuthenicationUtil checkEmailIsPresent() {
		return hold(user -> user.getEmail() != null && !user.getEmail().isEmpty(), "email", "email not present");
	}

	static AuthenicationUtil checkUserName() {
		return hold(user -> user.getUsername()!= null && !user.getUsername().isEmpty() , "username", "username not present");
	}

	static AuthenicationUtil checkUserPassword() {
		return hold(user -> user.getPassword() != null && !user.getPassword().isEmpty(), "password", "password not present");
	}

	static AuthenicationUtil hold(Predicate<RegistrationRequest> p, String field, String message) {

		return user -> p.test(user) ? new FieldError(null, null, true) : new FieldError(field, message, false);
	}
}
