package dev.nurlan.dao;

import dev.nurlan.model.MoneyTransfers;

public interface MoneyTransfersDao {

    void createMoneyTransfers(MoneyTransfers moneyTransfers) throws Exception;

    MoneyTransfers getMoneyTransfersById(Long mtId) throws Exception;

    void updateAcceptMoneyTransfers(MoneyTransfers moneyTransfers) throws Exception;
}
