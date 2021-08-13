package dev.nurlan.dao;

import dev.nurlan.model.Customer;
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
public class CustomerDaoImpl implements CustomerDao {


    @Autowired
    private DataSource dataSource;


    @Override
    public List<Customer> getCustomerList() {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CUSTOMER")
                .withFunctionName("GET_CUSTOMER_LIST")
                .declareParameters(new SqlOutParameter("RESULT", Types.REF_CURSOR, new BeanPropertyRowMapper<>(Customer.class)));
        Map<String, Object> result = caller.execute();
        return (List<Customer>) result.get("RESULT");
    }

    @Override
    public void createCustomer(Customer customer) {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CUSTOMER")
                .withProcedureName("CREATE_CUSTOMER");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_NAME", customer.getName())
                .addValue("P_SURNAME", customer.getSurname())
                .addValue("P_FNAME", customer.getFname())
                .addValue("P_MOBILE", customer.getMobile())
                .addValue("P_ADDRESS", customer.getAddress())
                .addValue("P_GENDER_ID", customer.getGenderId())
                .addValue("P_CUST_TYPE_ID", customer.getCustTypeId())
                .addValue("P_DOCUMENT_TYPE_ID", customer.getDocumentTypeId());
        caller.execute(param);
    }

    @Override
    public void updateCustomer(Customer customer) {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CUSTOMER")
                .withProcedureName("UPDATE_CUSTOMER");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_ID", customer.getId())
                .addValue("P_NAME", customer.getName())
                .addValue("P_SURNAME", customer.getSurname())
                .addValue("P_FNAME", customer.getFname())
                .addValue("P_MOBILE", customer.getMobile())
                .addValue("P_ADDRESS", customer.getAddress())
                .addValue("P_GENDER_ID", customer.getGenderId())
                .addValue("P_CUST_TYPE_ID", customer.getCustTypeId())
                .addValue("P_DOCUMENT_TYPE_ID", customer.getDocumentTypeId());
        caller.execute(param);
    }

    @Override
    public Customer getCustomerById(Long custId) throws Exception {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CUSTOMER")
                .withFunctionName("GET_CUSTOMER_BY_ID")
                .declareParameters(new SqlOutParameter("RESULT", Types.REF_CURSOR,
                        new BeanPropertyRowMapper<>(Customer.class)));
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_ID", custId);
        Map<String, Object> result = caller.execute(param);
        List<Customer> customerList = (List<Customer>) result.get("RESULT");
        if (customerList.size() > 0) {
            return customerList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void deleteCustomer(Long custId) {
        SimpleJdbcCall caller = new SimpleJdbcCall(dataSource);
        caller.withSchemaName("DEV_TEST")
                .withCatalogName("PACK_CUSTOMER")
                .withProcedureName("DELETE_CUSTOMER");
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("P_ID", custId);
        caller.execute(param);
    }
}
