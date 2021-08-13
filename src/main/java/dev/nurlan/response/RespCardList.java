package dev.nurlan.response;

import lombok.Data;

import java.util.List;

@Data
public class RespCardList {

    private List<RespCard> respCardList;
    private RespStatus status;
}
