package org.group32.pojo;


import lombok.*;

@Data
public class VTrShopMx extends POJO{

    private String tranChannel;
    private String orderCode;
    private String shopCode;
    private String shopName;
    private String hlwTranType;
    private String tranDate;
    private String tranTime;
    private double tranAmt;
    private String currentStatus;
    private double scoreNum;
    private String payChannel;
    private String uid;
    private String legalName;
    private String etlDt;

}
