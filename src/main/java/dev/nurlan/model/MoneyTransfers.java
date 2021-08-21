package dev.nurlan.model;

import lombok.Data;

import java.util.Date;

@Data
public class MoneyTransfers extends AbstractModel {

    private Long dtCustId;
    private Long crCustId;
    private Long dtCardId;
    private Long crCardId;
    private Float amount;
    private Integer curCode;
    private String transferCode;
    private Integer mtStateId;
    private String crName;
    private String crSurname;
    private String crFname;
    private String crMobile;
    private Integer transferTypeId;
    private Date acceptDate;
    private Date reverseDate;
    private Date dataDate;

}
