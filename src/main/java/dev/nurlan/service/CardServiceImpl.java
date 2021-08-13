package dev.nurlan.service;

import dev.nurlan.dao.CardDao;
import dev.nurlan.dao.CustomerDao;
import dev.nurlan.exception.ExceptionConstants;
import dev.nurlan.model.Card;
import dev.nurlan.model.Customer;
import dev.nurlan.request.ReqCard;
import dev.nurlan.response.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardDao cardDao;

    @Autowired
    private CustomerDao customerDao;


    @Override
    public RespStatus createCard(ReqCard reqCard) {

        RespStatus response = new RespStatus();

        try {

            if (reqCard.getCustId() == null || /*reqCard.getCardNumber() == null ||*/ reqCard.getCurCode() == null) {
                response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                response.setStatusMessage("Invalid request data");
                return response;
            }

            Customer customer = customerDao.getCustomerById(reqCard.getCustId());
            if (customer == null) {
                response.setStatusCode(ExceptionConstants.CUSTOMER_NOT_EXIST_AT_BANK);
                response.setStatusMessage("Customer not exist at bank");
                return response;
            }

            String cardNumber = RandomStringUtils.randomNumeric(16);

            Card card = new Card();
            card.setCustId(reqCard.getCustId());
            card.setCardNumber(cardNumber);
            card.setCurCode(reqCard.getCurCode());
            card.setCardBalance(reqCard.getCardBalance());
            cardDao.createCard(card);
            response.setStatusCode(RespStatus.getSuccessMessage().getStatusCode());
            response.setStatusMessage(RespStatus.getSuccessMessage().getStatusMessage());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(ExceptionConstants.INTERNAL_EXCEPTION);
            response.setStatusMessage("Internal exception");
            return response;
        }

        return response;
    }

    @Override
    public RespStatus deleteCard(ReqCard reqCard) {

        RespStatus response = new RespStatus();
        Long cardId = reqCard.getId();

        try {

            if (cardId == null) {
                response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                response.setStatusMessage("Invalid request data");
                return response;
            }

            Card card = cardDao.getCardById(cardId);
            if (card == null) {
                response.setStatusCode(ExceptionConstants.CARD_NOT_FOUND);
                response.setStatusMessage("Card not found");
                return response;
            }
            cardDao.deleteCard(cardId);
            response.setStatusCode(RespStatus.getSuccessMessage().getStatusCode());
            response.setStatusMessage(RespStatus.getSuccessMessage().getStatusMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(ExceptionConstants.INTERNAL_EXCEPTION);
            response.setStatusMessage("Internal exception");
            return response;
        }

        return response;
    }

    @Override
    public RespCardList getCardList() {

        RespCardList response = new RespCardList();

        try {
            List<RespCard> respCardList = new ArrayList<>();

            List<Card> cardList = cardDao.getCardList();
            if (cardList.isEmpty()) {
                response.setStatus(new RespStatus(ExceptionConstants.DATA_NOT_FOUND, "Data not found"));
                return response;
            }
            for (Card card : cardList) {
                RespCard respCard = new RespCard();
                respCard.setId(card.getId());
                respCard.setCustId(card.getCustId());
                respCard.setCardNumber(card.getCardNumber());
                respCard.setCardBalance(card.getCardBalance());
                respCard.setCurCode(card.getCurCode());
                respCardList.add(respCard);
            }
            response.setRespCardList(respCardList);
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            return response;
        }

        return response;
    }

    @Override
    public RespCard getCardById(ReqCard reqCard) {
        RespCard response = new RespCard();

        try {
            if (reqCard.getId() == null) {
                response.setStatus(new RespStatus(ExceptionConstants.INVALID_REQUEST_DATA, "Data not found"));
                return response;
            }

            Card card = cardDao.getCardById(reqCard.getId());
            if (card == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CARD_NOT_FOUND, "Card not found"));
                return response;
            }

            response.setId(card.getId());
            response.setCustId(card.getCustId());
            response.setCardNumber(card.getCardNumber());
            response.setCardBalance(card.getCardBalance());
            response.setCurCode(card.getCurCode());
            response.setCreateDate(card.getCreateDate());
            response.setStatus(RespStatus.getSuccessMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            return response;
        }
        return response;
    }

    @Override
    public RespStatus increaseCardBalance(ReqCard reqCard) {

        RespStatus response = new RespStatus();

        try {
            Long reqCardId = reqCard.getId();
            Float reqCardBalance = reqCard.getCardBalance();

            if (reqCardId == null || reqCardBalance == null) {
                response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                response.setStatusMessage("Invalid request data");
                return response;
            }

            if (reqCardBalance <= 0) {
                response.setStatusCode(ExceptionConstants.AMOUNT_MUST_BE_GREATER_THAN_ZERO);
                response.setStatusMessage("Amount must be greater than zero");
                return response;
            }

            Card card = cardDao.getCardById(reqCardId);

            if (card == null) {
                response.setStatusCode(ExceptionConstants.CARD_NOT_FOUND);
                response.setStatusMessage("Card not found");
                return response;
            }

            Float cardBlance = card.getCardBalance();
            Float commonCardBalance = cardBlance + reqCardBalance;

            card.setId(reqCardId);
            card.setCardBalance(commonCardBalance);
            cardDao.updateCardBalance(card);
            response.setStatusCode(RespStatus.getSuccessMessage().getStatusCode());
            response.setStatusMessage(RespStatus.getSuccessMessage().getStatusMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(ExceptionConstants.INTERNAL_EXCEPTION);
            response.setStatusMessage("Internal exception");
            return response;
        }

        return response;
    }
}
