package dev.nurlan.request;

import lombok.Data;

import java.util.Date;

@Data
public class ReqMoneyTransfers {

    private Long mtId;
    private Long dtCustId;
    private Long crCustId;
    private Long dtCardId;
    private Long crCardId;
    private Float amount;
    private Integer curCode;
    private String transferCode;
    private Integer mtStateId;
    private Integer transferTypeId;

    private String crName;
    private String crSurname;
    private String crFname;
    private String crMobile;
    private Date acceptDate;
    private Date reverseDate;

}
