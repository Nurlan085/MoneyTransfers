package dev.nurlan.dao;


import dev.nurlan.model.MoneyTransfers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class MoneyTransfersDaoImpl implements MoneyTransfersDao {

    @Autowired
    private DataSource dataSource;


    @Override
    public void createCardToCard(MoneyTransfers moneyTransfers) {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_MONEY_TRANSFERS")
                .withProcedureName("CREATE_MONEY_TRANSFERS");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_DT_CUST_ID", moneyTransfers.getDtCustId())
                .addValue("P_CR_CUST_ID", moneyTransfers.getCrCustId())
                .addValue("P_DT_CARD_ID", moneyTransfers.getDtCardId())
                .addValue("P_CR_CARD_ID", moneyTransfers.getCrCardId())
                .addValue("P_AMOUNT", moneyTransfers.getAmount())
                .addValue("P_CUR_CODE", moneyTransfers.getCurCode())
                .addValue("P_TRANSFER_CODE", moneyTransfers.getTransferCode())
                .addValue("P_MT_STATE_ID", moneyTransfers.getMtStateId())
                .addValue("P_CR_NAME", moneyTransfers.getCrName())
                .addValue("P_CR_SURNAME", moneyTransfers.getCrSurname())
                .addValue("P_CR_FNAME", moneyTransfers.getCrFname())
                .addValue("P_CR_MOBILE", moneyTransfers.getCrMobile())
                .addValue("P_TRANSFER_TYPE_ID", moneyTransfers.getTransferTypeId());
        //Hello
        caller.execute(param);
    }
}
