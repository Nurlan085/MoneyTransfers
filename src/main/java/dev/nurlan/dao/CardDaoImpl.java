package dev.nurlan.dao;

import dev.nurlan.model.Card;
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
public class CardDaoImpl implements CardDao {

    @Autowired
    private DataSource dataSource;

    @Override
    public void createCard(Card card) {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CARD")
                .withProcedureName("CREATE_CARD");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_CUST_ID", card.getCustId())
                .addValue("P_CARD_NUMBER", card.getCardNumber())
                .addValue("P_CUR_CODE", card.getCurCode())
                .addValue("P_CARD_BALANCE", card.getCardBalance());
        caller.execute(param);
    }

    @Override
    public void deleteCard(Long cardId) {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CARD")
                .withProcedureName("DELETE_CARD");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_ID", cardId);
        caller.execute(param);
    }

    @Override
    public Card getCardById(Long cardId) {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CARD")
                .withFunctionName("GET_CARD_BY_ID")
                .declareParameters(new SqlOutParameter("RESULT", Types.REF_CURSOR, new BeanPropertyRowMapper<>(Card.class)));
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_ID", cardId);
        Map<String, Object> result = caller.execute(param);
        List<Card> cardList = (List<Card>) result.get("RESULT");
        if (cardList.size() > 0) {
            return cardList.get(0);

        } else {
            return null;
        }
    }

    @Override
    public List<Card> getCardList() {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CARD")
                .withFunctionName("GET_CARD_LIST")
                .declareParameters(new SqlOutParameter("RESULT", Types.REF_CURSOR, new BeanPropertyRowMapper<>(Card.class)));
        Map<String, Object> result = caller.execute();
        return (List<Card>) result.get("RESULT");
    }

    @Override
    public void updateCardBalance(Card card) {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CARD")
                .withProcedureName("UPDATE_CARD_BALANCE");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_CARD_ID", card.getId())
                .addValue("P_CARD_BALANCE", card.getCardBalance());
        caller.execute(param);

    }
}
