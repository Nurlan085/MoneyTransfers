package dev.nurlan.dao;

import dev.nurlan.model.Customer;
import java.util.List;

public interface CustomerDao {

    List<Customer> getCustomerList() throws Exception;

    void createCustomer(Customer customer) throws Exception;

    void updateCustomer(Customer customer) throws Exception;

    Customer getCustomerById(Long custId) throws Exception;

    void deleteCustomer(Long custId) throws Exception;
}
