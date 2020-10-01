package com.cashcontrol.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cashcontrol.model.RegistrationRequest;


public class AuthenicationValidationUtil {

	public static List<FieldError> applyAll(RegistrationRequest user) {

		List<AuthenicationUtil> ruleList = new ArrayList<>();
		ruleList.add(AuthenicationUtil.checkUserName());
		ruleList.add(AuthenicationUtil.checkEmailIsPresent());
		ruleList.add(AuthenicationUtil.checkUserPassword());

		return ruleList.stream().map(u -> u.apply(user)).filter(r -> !r.isValid()).collect(Collectors.toList());
	}
}
