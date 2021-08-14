package dev.nurlan.service;

import dev.nurlan.dao.CardDao;
import dev.nurlan.dao.CustomerDao;
import dev.nurlan.dao.MoneyTransfersDao;
import dev.nurlan.enums.EnumMoneyTransfersState;
import dev.nurlan.enums.EnumTransferTypeId;
import dev.nurlan.exception.ExceptionConstants;
import dev.nurlan.model.Card;
import dev.nurlan.model.Customer;
import dev.nurlan.model.MoneyTransfers;
import dev.nurlan.request.ReqMoneyTransfers;
import dev.nurlan.response.RespMoneyTransfers;
import dev.nurlan.response.RespStatus;
import dev.nurlan.util.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class MoneyTransfersServiceImpl implements MoneyTransfersService {

    private static final Logger LOGGER = LogManager.getLogger(MoneyTransfersServiceImpl.class);

    private static final String mt = "TM";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MoneyTransfersDao moneyTransfersDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private CustomerDao customerDao;


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
                LOGGER.info("Ip: " + Utility.getClientIp(request) + ", Invalid request data");
                return response;
            }

            if (dtCardId.equals(crCardId)) {
                response.setStatus(new RespStatus(ExceptionConstants.CARDS_IS_THE_SAME, "Cards is the same"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Cards is the same");
                return response;
            }

            Customer dtCustomer = customerDao.getCustomerById(dtCustId);
            Customer crCustomer = customerDao.getCustomerById(crCustId);

            if (dtCustomer == null || crCustomer == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CUSTOMER_NOT_EXIST_AT_BANK, "Customer not exist at bank"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Customer not exist at bank");
                return response;
            }

            if (reqMoneyTransfers.getAmount() <= 0) {
                response.setStatus(new RespStatus(ExceptionConstants.AMOUNT_MUST_BE_GREATER_THAN_ZERO, "Amount must be greater than zero"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Amount must be greater than zero");
                return response;
            }

            Card dtCard = cardDao.getCardById(dtCardId);
            if (dtCard == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CARD_NOT_FOUND, "Card not found"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Card not found");
                return response;
            }

            if (!dtCard.getCustId().equals(dtCustId)) {
                response.setStatus(new RespStatus(ExceptionConstants.THE_CUSTOMER_DOES_NOT_HAVE_SUCH_A_CARD, "The Debitor customer does not have such a card"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", The customer does not have such a card");
                return response;
            }


            if (dtCard.getCardBalance() < dtAmount) {
                response.setStatus(new RespStatus(ExceptionConstants.THE_AMOUNT_YOU_SENT_IS_NOT_ON_THE_CARD, "The amount you sent is not on the card"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", The amount you sent is not on the card");
                return response;
            }

            Card crCard = cardDao.getCardById(crCardId);
            if (crCard == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CARD_NOT_FOUND, "Creditor Card not found"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Creditor card not found");
                return response;
            }

            if (!crCard.getCustId().equals(crCustId)) {
                response.setStatus(new RespStatus(ExceptionConstants.THE_CUSTOMER_DOES_NOT_HAVE_SUCH_A_CARD, "The Creditor customer does not have such a card"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", The Creditor customer does not have such a card");
                return response;
            }

            if (!curCode.equals(dtCard.getCurCode())) {
                response.setStatus(new RespStatus(ExceptionConstants.SENT_CURRENCY_NOT_THE_SAME_CARD_CURRENCY, "Sent currency not the same card currency"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Sent currency not the same card currency");
                return response;
            }

            if (!dtCard.getCurCode().equals(crCard.getCurCode())) {
                response.setStatus(new RespStatus(ExceptionConstants.CARD_CURRENCIES_ARE_NOT_THE_SAME, "Card currencies are not the same"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Card currencies are not the same");
                return response;
            }

            String transferCode = mt + RandomStringUtils.randomNumeric(8);

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
            moneyTransfersDao.createMoneyTransfers(moneyTransfers);

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
            LOGGER.warn("Ip: " + Utility.getClientIp(request) + "response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            return response;
        }
        return response;
    }

    @Override
    public RespMoneyTransfers cardToNoAccount(ReqMoneyTransfers reqMoneyTransfers) {
        RespMoneyTransfers response = new RespMoneyTransfers();

        try {
            LOGGER.info("Ip: " + Utility.getClientIp(request) + ", called cardToNoAccount");

            Long dtCustId = reqMoneyTransfers.getDtCustId();
            Long dtCardId = reqMoneyTransfers.getDtCardId();
            Float dtAmount = reqMoneyTransfers.getAmount();
            Integer curCode = reqMoneyTransfers.getCurCode();
            String crName = reqMoneyTransfers.getCrName();
            String crSurname = reqMoneyTransfers.getCrSurname();
            String crFname = reqMoneyTransfers.getCrFname();
            String crMobile = reqMoneyTransfers.getCrMobile();
            Integer transferTypeId = reqMoneyTransfers.getTransferTypeId();
            String transferCode = mt + RandomStringUtils.randomNumeric(8);

            if (dtCustId == null || dtCardId == null || dtAmount == null || curCode == null || transferTypeId == null
                    || (crName == null || crName.isEmpty()) || (crSurname == null || crSurname.isEmpty()) ||
                    (crFname == null || crFname.isEmpty()) || (crMobile == null || crMobile.isEmpty())) {
                response.setStatus(new RespStatus(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data"));
                LOGGER.info("Ip: " + Utility.getClientIp(request) + ", Invalid request data");
                return response;
            }

            Customer dtCustomer = customerDao.getCustomerById(dtCustId);

            if (dtCustomer == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CUSTOMER_NOT_EXIST_AT_BANK, "Customer not exist at bank"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Customer not exist at bank");
                return response;
            }

            if (dtAmount <= 0) {
                response.setStatus(new RespStatus(ExceptionConstants.AMOUNT_MUST_BE_GREATER_THAN_ZERO, "Amount must be greater than zero"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Amount must be greater than zero");
                return response;
            }

            Card dtCard = cardDao.getCardById(dtCardId);

            if (dtCard == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CARD_NOT_FOUND, "Card not found"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Card not found");
                return response;
            }

            if (!dtCard.getCustId().equals(dtCustId)) {
                response.setStatus(new RespStatus(ExceptionConstants.THE_CUSTOMER_DOES_NOT_HAVE_SUCH_A_CARD, "The Debitor customer does not have such a card"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", The customer does not have such a card");
                return response;
            }


            if (dtCard.getCardBalance() < dtAmount) {
                response.setStatus(new RespStatus(ExceptionConstants.THE_AMOUNT_YOU_SENT_IS_NOT_ON_THE_CARD, "The amount you sent is not on the card"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", The amount you sent is not on the card");
                return response;
            }

            if (!curCode.equals(dtCard.getCurCode())) {
                response.setStatus(new RespStatus(ExceptionConstants.SENT_CURRENCY_NOT_THE_SAME_CARD_CURRENCY, "Sent currency not the same card currency"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Sent currency not the same card currency");
                return response;
            }

            Float dtCardBlance = dtCard.getCardBalance() - dtAmount;
            dtCard.setId(dtCardId);
            dtCard.setCardBalance(dtCardBlance);
            cardDao.updateCardBalance(dtCard);

            MoneyTransfers moneyTransfers = new MoneyTransfers();
            moneyTransfers.setDtCustId(dtCustId);
            moneyTransfers.setDtCardId(dtCardId);
            moneyTransfers.setAmount(dtAmount);
            moneyTransfers.setCurCode(curCode);
            moneyTransfers.setCrName(crName);
            moneyTransfers.setCrSurname(crSurname);
            moneyTransfers.setCrFname(crFname);
            moneyTransfers.setCrMobile(crMobile);
            moneyTransfers.setTransferCode(transferCode);
            moneyTransfers.setTransferTypeId(transferTypeId);
            moneyTransfers.setMtStateId(EnumMoneyTransfersState.WAITING.getValue());
            moneyTransfersDao.createMoneyTransfers(moneyTransfers);

            response.setDtCustId(dtCustId);
            response.setDtCardId(dtCardId);
            response.setAmount(dtAmount);
            response.setCurCode(curCode);
            response.setCrName(crName);
            response.setCrSurname(crSurname);
            response.setCrFname(crFname);
            response.setCrMobile(crMobile);
            response.setTransferCode(transferCode);
            response.setTransferTypeId(transferTypeId);
            response.setMtStateId(EnumMoneyTransfersState.WAITING.getValue());
            response.setStatus(RespStatus.getSuccessMessage());
            LOGGER.warn("Ip: " + Utility.getClientIp(request) + "response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            LOGGER.error("Ip: " + Utility.getClientIp(request) + ", error: " + e);
            return response;
        }
        return response;
    }

    @Override
    public RespStatus acceptCardToNoAccount(ReqMoneyTransfers reqMoneyTransfers) {

        RespStatus response = new RespStatus();

        try {
            LOGGER.info("Ip: " + Utility.getClientIp(request) + ", called acceptCardToNoAccount");

            Long mtId = reqMoneyTransfers.getMtId();
            Long crCustId = reqMoneyTransfers.getCrCustId();

            if (crCustId == null || mtId == null) {
                response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                response.setStatusMessage("Invalid request data");
                LOGGER.info("Ip: " + Utility.getClientIp(request) + ", Invalid request data");
                return response;
            }

            Customer crCustomer = customerDao.getCustomerById(crCustId);
            if (crCustomer == null) {
                response.setStatusCode(ExceptionConstants.CUSTOMER_NOT_EXIST_AT_BANK);
                response.setStatusMessage("Customer not exist at bank");
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Customer not exist at bank");
                return response;
            }

            MoneyTransfers moneyTransfers = moneyTransfersDao.getMoneyTransfersById(mtId);
            if (moneyTransfers == null || moneyTransfers.getTransferTypeId().equals(EnumTransferTypeId.CARD_TO_CARD.getValue())) {
                response.setStatusCode(ExceptionConstants.TRANSFER_NOT_FOUND);
                response.setStatusMessage("Transfer not found");
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Transfer not found");
                return response;
            }

            if (moneyTransfers.getMtStateId().equals(EnumMoneyTransfersState.ACCEPT.getValue())) {
                response.setStatusCode(ExceptionConstants.TRANSFER_ACCEPTED);
                response.setStatusMessage("Transfer accepted");
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Transfer accepted");
                return response;
            }

            if (moneyTransfers.getMtStateId().equals(EnumMoneyTransfersState.REVERSE.getValue())) {
                response.setStatusCode(ExceptionConstants.TRANSFER_REVERSED);
                response.setStatusMessage("Transfer reversed");
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Transfer reversed");
                return response;
            }

            moneyTransfers.setId(mtId);
            moneyTransfers.setCrCustId(crCustId);
            moneyTransfers.setMtStateId(EnumMoneyTransfersState.ACCEPT.getValue());
            response.setStatusCode(RespStatus.getSuccessMessage().getStatusCode());
            moneyTransfersDao.updateAcceptMoneyTransfers(moneyTransfers);
            response.setStatusMessage(RespStatus.getSuccessMessage().getStatusMessage());
            LOGGER.warn("Ip: " + Utility.getClientIp(request) + "response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(ExceptionConstants.INTERNAL_EXCEPTION);
            response.setStatusMessage("Internal exception");
            LOGGER.error("Ip: " + Utility.getClientIp(request) + ", error: " + e);
            return response;
        }
        return response;
    }
}
