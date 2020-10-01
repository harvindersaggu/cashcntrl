package com.cashcontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MailMessageDto {

  private String from;

  private String replyTo;

  private String[] to;

  private String[] cc;

  private String[] bcc;

  private LocalDate sentDate;

  private String subject;

  private String text;

  private String content;

//  private String template;

}
