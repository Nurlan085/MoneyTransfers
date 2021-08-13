package dev.nurlan.dao;

import dev.nurlan.model.MoneyTransfers;

public interface MoneyTransfersDao {

    void createCardToCard(MoneyTransfers moneyTransfers) throws Exception;
}
