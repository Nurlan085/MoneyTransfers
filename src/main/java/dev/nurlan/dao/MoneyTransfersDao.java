package dev.nurlan.dao;

import dev.nurlan.model.MoneyTransfers;

import java.util.List;

public interface MoneyTransfersDao {

    void createMoneyTransfers(MoneyTransfers moneyTransfers) throws Exception;

    MoneyTransfers getMoneyTransfersById(Long mtId) throws Exception;

    void updateAcceptMoneyTransfers(MoneyTransfers moneyTransfers) throws Exception;

    void updateReverseMoneyTransfers(MoneyTransfers moneyTransfers) throws Exception;

    List<MoneyTransfers> getMoneyTransfersList() throws Exception;
}
