package dev.nurlan.service;

import dev.nurlan.dao.CardDao;
import dev.nurlan.dao.CustomerDao;
import dev.nurlan.dao.MoneyTransfersDao;
import dev.nurlan.enums.EnumMoneyTransfersState;
import dev.nurlan.exception.ExceptionConstants;
import dev.nurlan.model.Card;
import dev.nurlan.model.Customer;
import dev.nurlan.model.MoneyTransfers;
import dev.nurlan.request.ReqMoneyTransfers;
import dev.nurlan.response.RespMoneyTransfers;
import dev.nurlan.response.RespStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransfersServiceImpl implements MoneyTransfersService {


    @Autowired
    private MoneyTransfersDao moneyTransfersDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private CustomerDao customerDao;

    private static final String tm = "TM";


    @Override
    public RespMoneyTransfers cardToCard(ReqMoneyTransfers reqMoneyTransfers) {

        RespMoneyTransfers response = new RespMoneyTransfers();

        try {
            Long dtCustId = reqMoneyTransfers.getDtCustId();
            Long crCustId = reqMoneyTransfers.getCrCustId();
            Long dtCardId = reqMoneyTransfers.getDtCardId();
            Long crCardId = reqMoneyTransfers.getCrCardId();
            Integer curCode = reqMoneyTransfers.getCurCode();
            Float dtAmount = reqMoneyTransfers.getAmount();
            Integer transferTypeId = reqMoneyTransfers.getTransferTypeId();

            if (dtCustId == null || crCustId == null || dtCardId == null || crCardId == null
                    || dtAmount == null || curCode == null || transferTypeId == null) {
                response.setStatus(new RespStatus(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data"));
                return response;
            }

            if (dtCardId.equals(crCardId)) {
                response.setStatus(new RespStatus(ExceptionConstants.CARDS_IS_THE_SAME, "Cards is the same"));
                return response;
            }

            Customer dtCustomer = customerDao.getCustomerById(dtCustId);
            Customer crCustomer = customerDao.getCustomerById(crCustId);


            if (dtCustomer == null || crCustomer == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CUSTOMER_NOT_EXIST_AT_BANK, "Customer not exist at bank"));
                return response;
            }

            if (reqMoneyTransfers.getAmount() <= 0) {
                response.setStatus(new RespStatus(ExceptionConstants.AMOUNT_MUST_BE_GREATER_THAN_ZERO, "Amount must be greater than zero"));
                return response;
            }

            Card dtCard = cardDao.getCardById(dtCardId);
            if (dtCard == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CARD_NOT_FOUND, "Card not found"));
                return response;
            }

            if (!dtCard.getCustId().equals(dtCustId)) {
                response.setStatus(new RespStatus(ExceptionConstants.THE_CUSTOMER_DOES_NOT_HAVE_SUCH_A_CARD, "The Debitor customer does not have such a card"));
                return response;
            }


            if (dtCard.getCardBalance() < dtAmount) {
                response.setStatus(new RespStatus(ExceptionConstants.THE_AMOUNT_YOU_SENT_IS_NOT_ON_THE_CARD, "The amount you sent is not on the card"));
                return response;
            }

            Card crCard = cardDao.getCardById(crCardId);
            if (crCard == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CARD_NOT_FOUND, "Creditor Card not found"));
                return response;
            }

            if (!crCard.getCustId().equals(crCustId)) {
                response.setStatus(new RespStatus(ExceptionConstants.THE_CUSTOMER_DOES_NOT_HAVE_SUCH_A_CARD, "The Creditor customer does not have such a card"));
                return response;
            }

            if (!curCode.equals(dtCard.getCurCode())) {
                response.setStatus(new RespStatus(ExceptionConstants.SENT_CURRENCY_NOT_THE_SAME_CARD_CURRENCY, "Sent currency not the same card currency"));
                return response;
            }

            if (!dtCard.getCurCode().equals(crCard.getCurCode())) {
                response.setStatus(new RespStatus(ExceptionConstants.CARD_CURRENCIES_ARE_NOT_THE_SAME, "Card currencies are not the same"));
                return response;
            }

            String transferCode = tm + RandomStringUtils.randomNumeric(8);

            Float dtCardBlance = dtCard.getCardBalance() - dtAmount;
            dtCard.setId(dtCardId);
            dtCard.setCardBalance(dtCardBlance);
            cardDao.updateCardBalance(dtCard);

            Float crCardBlance = dtAmount + crCard.getCardBalance();
            crCard.setId(crCardId);
            crCard.setCardBalance(crCardBlance);
            cardDao.updateCardBalance(crCard);

            MoneyTransfers moneyTransfers = new MoneyTransfers();
            moneyTransfers.setDtCustId(dtCustId);
            moneyTransfers.setCrCustId(crCustId);
            moneyTransfers.setDtCardId(dtCardId);
            moneyTransfers.setCrCardId(crCardId);
            moneyTransfers.setAmount(dtAmount);
            moneyTransfers.setCurCode(curCode);
            moneyTransfers.setTransferCode(transferCode);
            moneyTransfers.setMtStateId(EnumMoneyTransfersState.SUCCESS.getValue());
            moneyTransfers.setTransferTypeId(transferTypeId);
            moneyTransfersDao.createCardToCard(moneyTransfers);

            response.setDtCustId(dtCustId);
            response.setCrCustId(crCustId);
            response.setDtCardId(dtCardId);
            response.setCrCardId(crCardId);
            response.setAmount(dtAmount);
            response.setCurCode(curCode);
            response.setTransferCode(transferCode);
            response.setTransferTypeId(transferTypeId);
            response.setMtStateId(EnumMoneyTransfersState.SUCCESS.getValue());
            response.setStatus(RespStatus.getSuccessMessage());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            return response;
        }
        return response;
    }
}
