package com.cashcontrol.payload;

import org.springframework.http.HttpStatus;

import com.cashcontrol.enums.CustomErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {

	@JsonProperty("is_success")
	private boolean isSuccess;

	private String message;

	private Object data;

	private CustomErrorCode customErrorCode;

	private HttpStatus httpStatusCode;

	public ApiResponse(boolean isSuccess, String message, Object data, CustomErrorCode customErrorCode,
			HttpStatus httpStatusCode) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.data = data;
		this.customErrorCode = customErrorCode;
		this.httpStatusCode = httpStatusCode;
	}

	public ApiResponse(boolean isSuccess, String message, Object data, HttpStatus httpStatusCode) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.data = data;
		this.httpStatusCode = httpStatusCode;
	}

	public ApiResponse(boolean isSuccess, String message, HttpStatus httpStatusCode) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.httpStatusCode = httpStatusCode;
	}

	public ApiResponse(boolean isSuccess, String message, CustomErrorCode customErrorCode, HttpStatus httpStatusCode) {
		this.isSuccess = isSuccess;
		this.message = message;
		this.customErrorCode = customErrorCode;
		this.httpStatusCode = httpStatusCode;
	}

	public ApiResponse() {

	}

	@JsonProperty("is_success")
	public boolean isSuccess() {
		return isSuccess;
	}

	@JsonProperty("is_success")
	public void setSuccess(boolean success) {
		isSuccess = success;
	}

	public CustomErrorCode getCustomErrorCode() {
		return customErrorCode;
	}

	public void setCustomErrorCode(CustomErrorCode customErrorCode) {
		this.customErrorCode = customErrorCode;
	}

	public HttpStatus getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatus httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
