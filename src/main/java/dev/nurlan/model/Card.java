package dev.nurlan.model;

import lombok.Data;
import java.util.Date;

@Data
public class Card extends AbstractModel {

    private Long custId;
    private String cardNumber;
    private Integer curCode;
    private Float cardBalance;
    private Date createDate;
    private Date expireDate;

}
