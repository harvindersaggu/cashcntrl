package com.cashcontrol.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cashcontrol.enums.LedgerType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "leder_summary")
public class LedgerSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	private Date transactionDate;

	private String particulars;

	private String vchType;

	private String vchNo;

	private String refNo;

	private String paidTo;

	private String currency;

	private String fcAmountDr;

	private String fcAmountCr;

	private String cumulativeBalFc;

	private String exchRate;

	private String cumulativeBal;

	private String chequeNo;

	private Date chequeDate;

	private String chequeAdvice;

	private String chequeStatus;

	private String narration;

	private String shortNarration;

	@Enumerated(EnumType.STRING)
	private LedgerType type;

	private Double amount;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "scenario_id")
	private Scenario scenario;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "organisation_id")
	private Organisation organisation;
}
