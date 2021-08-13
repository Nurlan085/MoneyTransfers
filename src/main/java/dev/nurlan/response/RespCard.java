package dev.nurlan.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RespCard {

    @JsonProperty(value = "cardId")
    private Long Id;
    private Long custId;
    private String cardNumber;
    private Integer curCode;
    private Float cardBalance;
    private RespStatus status;
    private Date createDate;
    private Date expireDate;

}
