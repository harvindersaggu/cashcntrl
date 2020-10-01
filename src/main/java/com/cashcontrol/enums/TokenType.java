package com.cashcontrol.enums;

public enum TokenType {

	AUTHORIZATION_TOKEN(0), REFRESH_TOKEN(1), VERIFICATION_TOKEN(2), PASSWORD_RESET_TOKEN(3), EMAIL_RESET(4),
	PASSWORD_RESET_AUTH_TOKEN(5), RESEND_VERIFICATION_TOKEN(6), RESEND_PASSWORD_RESET_TOKEN(7),
	NEW_EMAIL_PASSWORD_RESET_TOKEN(8);

	private int tokenTypeId;

	public int getTokenTypeId() {
		return tokenTypeId;
	}

	public void setTokenTypeId(int tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}

	TokenType(int tokenTypeId) {
		this.tokenTypeId = tokenTypeId;
	}
}
