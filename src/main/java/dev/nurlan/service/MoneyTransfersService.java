package dev.nurlan.service;

import dev.nurlan.request.ReqMoneyTransfers;
import dev.nurlan.response.RespMoneyTransfers;

public interface MoneyTransfersService {

    RespMoneyTransfers cardToCard(ReqMoneyTransfers reqMoneyTransfers);

    RespMoneyTransfers cardToNoAccount(ReqMoneyTransfers reqMoneyTransfers);

}
