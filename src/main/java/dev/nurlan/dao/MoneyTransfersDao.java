package dev.nurlan.dao;

import dev.nurlan.model.MoneyTransfers;

public interface MoneyTransfersDao {

    void createMoneyTransfers(MoneyTransfers moneyTransfers) throws Exception;
}
