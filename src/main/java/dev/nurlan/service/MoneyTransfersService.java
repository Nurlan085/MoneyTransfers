package dev.nurlan.service;

import dev.nurlan.request.ReqCustomer;
import dev.nurlan.request.ReqMoneyTransfers;
import dev.nurlan.response.RespMoneyTransfers;
import dev.nurlan.response.RespStatus;

public interface MoneyTransfersService {

    RespMoneyTransfers cardToCard(ReqMoneyTransfers reqMoneyTransfers);

    RespMoneyTransfers cardToNoAccount(ReqMoneyTransfers reqMoneyTransfers);

    RespStatus acceptCardToNoAccount(ReqMoneyTransfers reqMoneyTransfers);

    RespStatus reverseCardToNoAccount(ReqMoneyTransfers reqMoneyTransfers);

    String testBrCard(ReqCustomer request);

}
