package dev.nurlan.scheduler;

import dev.nurlan.dao.CardDao;
import dev.nurlan.dao.MoneyTransfersDao;
import dev.nurlan.enums.EnumMoneyTransfersState;
import dev.nurlan.model.Card;
import dev.nurlan.model.MoneyTransfers;
import dev.nurlan.util.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MoneyTransfersScheduler {

    private static final Logger LOGGER = LogManager.getLogger(MoneyTransfersScheduler.class);

    @Autowired
    private MoneyTransfersDao moneyTransfersDao;

    @Autowired
    private CardDao cardDao;

    @Scheduled(fixedRate = 10000)
    public void checkTransfersReverse() {

        LOGGER.info("MoneyTransfersScheduler is start");
        try {
            List<MoneyTransfers> moneyTransfersList = moneyTransfersDao.getMoneyTransfersList();

            for (MoneyTransfers moneyTransfers : moneyTransfersList) {

                if (moneyTransfers.getMtStateId().equals(EnumMoneyTransfersState.WAITING.getValue())) {
                    long dayCount = Utility.getCountBetweenDays(moneyTransfers.getDataDate(),new Date());

                    if (dayCount >= 7) {
                        Card card = cardDao.getCardById(moneyTransfers.getDtCardId());
                        Float reverseAmount = card.getCardBalance() + moneyTransfers.getAmount();
                        card.setCardBalance(reverseAmount);
                        cardDao.updateCardBalance(card);
                        moneyTransfers.setMtStateId(EnumMoneyTransfersState.REVERSE.getValue());
                        moneyTransfersDao.updateReverseMoneyTransfers(moneyTransfers);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("MoneyTransfersScheduler, Internal Exception");
        }
        LOGGER.info("MoneyTransfersScheduler is ended");
    }
}
