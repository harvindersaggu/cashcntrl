package com.cashcontrol.util;

public class FieldError {

	String fieldName;
	String fieldMessage;
	boolean isValid;

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	@Override
	public String toString() {
		return "FieldError [fieldName=" + fieldName + ", fieldMessage=" + fieldMessage + "]";
	}

	public FieldError(String fieldName, String fieldMessage, boolean isValid) {
		super();
		this.fieldName = fieldName;
		this.fieldMessage = fieldMessage;
		this.isValid = isValid;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldMessage() {
		return fieldMessage;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldMessage(String fieldMessage) {
		this.fieldMessage = fieldMessage;
	}

}
