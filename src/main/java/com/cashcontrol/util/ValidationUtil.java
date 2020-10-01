package com.cashcontrol.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ValidationUtil {

	Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za"
			+ "-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public boolean validateEmail(final String email) {
		if (StringUtils.isEmpty(email)) {
			return false;
		}
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public boolean isStringEmpty(final String field) {
		if (StringUtils.isEmpty(field)) {
			return false;
		}
		return true;
	}

	public boolean validatePassword(String password) {

		PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
				// at least 8 characters
				new LengthRule(8, 1024),
				// at least one upper-case character
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				// at least one lower-case character
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				// at least one digit character
				new CharacterRule(EnglishCharacterData.Digit, 1),
				// at least one special character
				new CharacterRule(EnglishCharacterData.Special, 1),
				// no whitespace
				new WhitespaceRule()));
		RuleResult result = passwordValidator.validate(new PasswordData(password));

		if (result.isValid()) {
			return true;
		} else {
			List<String> messages = passwordValidator.getMessages(result);
			String messageTemplate = String.join(",", messages);
			// Log this messageTemplate
			return false;
		}
	}

	public boolean isNull(Object object) {
		if (object == null)
			return false;
		return true;

	}

	public boolean isValidDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH);
		format.setLenient(false);
		try {
			format.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public boolean isObjectPresent(Object object) {
		if (Optional.ofNullable(object).isPresent()) {
			return true;
		}
		return false;
	}

	public boolean isValidDates(String value) {
		String regex = "(?!0000)\\d{4}[-](((0)[1-9])|((1)[0-2]))";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

}
