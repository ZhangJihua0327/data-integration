package org.group32.pojo;


import lombok.*;

@Data
public class DmVTrDuebillMx extends POJO{

    private String uid;
    private String acctNo;
    private String receiptNo;
    private String contractNo;
    private String subjectNo;
    private String custNo;
    private String loanCustNo;
    private String custName;
    private String bussType;
    private String currType;
    private double bussAmt;
    private String putoutDate;
    private String matuDate;
    private String actuMatuDate;
    private double bussRate;
    private double actuBussRate;
    private String intrType;
    private String intrCyc;
    private long payTimes;
    private String payCyc;
    private long extendTimes;
    private double bal;
    private double normBal;
    private double dlayAmt;
    private double dullAmt;
    private double badDebtAmt;
    private double owedIntIn;
    private double owedIntOut;
    private double finePrInt;
    private double fineIntrInt;
    private long dlayDays;
    private String payAcct;
    private String putoutAcct;
    private String payBackAcct;
    private long dueIntrDays;
    private String operateOrg;
    private String operator;
    private String regOrg;
    private String register;
    private String occurDate;
    private String loanUse;
    private String payType;
    private String payFreq;
    private String vouchType;
    private String mgrNo;
    private String mgeOrg;
    private String loanChannel;
    private String tenClass;
    private String srcDt;
    private String etlDt;


}
