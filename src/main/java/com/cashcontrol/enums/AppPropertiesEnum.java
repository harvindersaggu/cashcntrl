package com.cashcontrol.enums;

public enum AppPropertiesEnum {

  JWT_TOKEN_SECRET_KEY("jwt.secret"),
  ENCRYPTION_SECRET_KEY("encryption.secret"),
  TOKEN_EXPIRY_IN_SECS_KEY("token.expiry.seconds"),
  ACCESS_TOKEN_EXPIRY_IN_SECS_KEY("jwt.access.token.expiration.seconds"),
  REFRESH_TOKEN_EXPIRY_IN_SECS_KEY("jwt.refresh.token.expiration.seconds"),

  MAIL_HOST_KEY("spring.mail.host"),
  MAIL_PORT_KEY("spring.mail.port"),
  MAIL_USERNAME_KEY("spring.mail.username"),
  MAIL_PASSWORD_KEY("spring.mail.password"),
  MAIL_FROM_KEY("mail.from"),
  MAIL_SMTP_AUTH_KEY("spring.mail.properties.mail.smtp.auth"),
  MAIL_SMTP_CONNECTION_TIMEOUT_KEY("spring.mail.properties.mail.smtp.connectiontimeout"),
  MAIL_SMTP_TIMEOUT_KEY("spring.mail.properties.mail.smtp.timeout"),
  MAIL_SMTP_WRITE_TIMEOUT_KEY("spring.mail.properties.mail.smtp.writetimeout"),
  MAIL_SMTP_STARTTLS_ENABLE_KEY("spring.mail.properties.mail.smtp.starttls.enable"),
  MAIL_SMTP_STARTTLS_REQUIRED_KEY("spring.mail.properties.mail.smtp.starttls.required"),
  AMAZON_PROPERTIES_ENDPOINTURL("amazonProperties.endpointUrl"),
  AMAZON_PROPERTIES_ACCESSKEY("amazonProperties.accessKey"),
  AMAZON_PROPERTIES_SECRETKEY("amazonProperties.secretKey"),
  AMAZON_PROPERTIES_BUCKETNAME("amazonProperties.bucketName");
  private String propName;

  AppPropertiesEnum(String propName) {
    this.propName = propName;
  }

  public String getPropName() {
    return propName;
  }

  public void setPropName(String propName) {
    this.propName = propName;
  }
}
