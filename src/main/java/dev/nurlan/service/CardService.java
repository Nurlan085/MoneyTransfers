package dev.nurlan.service;

import dev.nurlan.request.ReqCard;
import dev.nurlan.response.RespCard;
import dev.nurlan.response.RespCardList;
import dev.nurlan.response.RespStatus;

public interface CardService {

    RespStatus createCard(ReqCard reqCard);

    RespStatus deleteCard(ReqCard reqCard);

    RespCardList getCardList();

    RespCard getCardById(ReqCard reqCard);

    RespStatus increaseCardBalance(ReqCard reqCard);
}
