package dev.nurlan.service;

import dev.nurlan.request.ReqCustomer;
import dev.nurlan.response.RespCustomer;
import dev.nurlan.response.RespCustomerList;
import dev.nurlan.response.RespStatus;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    RespCustomerList getCustomerList();

    RespStatus createCustomer(ReqCustomer reqCustomer);

    RespStatus updateCustomer(ReqCustomer reqCustomer);

    RespCustomer getCustomerById(Long custId);

    RespStatus deleteCustomer(Long custId);
}
