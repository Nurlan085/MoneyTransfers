package dev.nurlan.dao;


import dev.nurlan.model.MoneyTransfers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Map;


@Repository
public class MoneyTransfersDaoImpl implements MoneyTransfersDao {

    @Autowired
    private DataSource dataSource;


    @Override
    public void createMoneyTransfers(MoneyTransfers moneyTransfers) {
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
        caller.execute(param);
    }

    @Override
    public MoneyTransfers getMoneyTransfersById(Long mtId) throws Exception {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_MONEY_TRANSFERS")
                .withFunctionName("GET_MONEY_TRANSFERS_BY_ID")
                .declareParameters(new SqlOutParameter("RESULT", Types.REF_CURSOR, new BeanPropertyRowMapper<>(MoneyTransfers.class)));
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_MT_ID", mtId);
        Map<String, Object> result = caller.execute(param);
        List<MoneyTransfers> moneyTransfersList = (List<MoneyTransfers>) result.get("RESULT");
        if (moneyTransfersList.size() > 0) {
            return moneyTransfersList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void updateAcceptMoneyTransfers(MoneyTransfers moneyTransfers) throws Exception {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_MONEY_TRANSFERS")
                .withProcedureName("UPDATE_ACCEPT_MONEY_TRANSFERS");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_MT_ID", moneyTransfers.getId())
                .addValue("P_CR_CUST_ID", moneyTransfers.getCrCustId())
                .addValue("P_MT_STATE_ID", moneyTransfers.getMtStateId());
        caller.execute(param);
    }
}
