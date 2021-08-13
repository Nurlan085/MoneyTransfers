package dev.nurlan.controller;

import dev.nurlan.request.ReqCustomer;
import dev.nurlan.response.RespCustomer;
import dev.nurlan.response.RespCustomerList;
import dev.nurlan.response.RespStatus;
import dev.nurlan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = "/getCustomerList", method = {RequestMethod.GET, RequestMethod.POST})
    public RespCustomerList getCustomerList() {
        return customerService.getCustomerList();
    }


    @RequestMapping(value = "/getCustomerById/{custId}", method = {RequestMethod.GET, RequestMethod.POST})
    public RespCustomer getCustomerById(@PathVariable(value = "custId") Long custId) {
        return customerService.getCustomerById(custId);
    }


    @PostMapping(value = "createCustomer")
    public RespStatus createCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.createCustomer(reqCustomer);
    }


    @PostMapping(value = "updateCustomer")
    public RespStatus updateCustomer(@RequestBody ReqCustomer reqCustomer) {
        return customerService.updateCustomer(reqCustomer);
    }

    @PostMapping(value = "deleteCustomer/{custId}")
    public RespStatus deleteCustomer(@PathVariable(value = "custId") Long custId) {
        return customerService.deleteCustomer(custId);
    }


}
