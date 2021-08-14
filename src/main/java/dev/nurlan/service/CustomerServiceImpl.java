package dev.nurlan.service;

import dev.nurlan.dao.CustomerDao;
import dev.nurlan.enums.EnumCustomerType;
import dev.nurlan.exception.ExceptionConstants;
import dev.nurlan.model.Customer;
import dev.nurlan.request.ReqCustomer;
import dev.nurlan.response.RespCustomer;
import dev.nurlan.response.RespCustomerList;
import dev.nurlan.response.RespStatus;
import dev.nurlan.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class CustomerServiceImpl implements CustomerService {


    private static final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CustomerDao customerDao;


    @Override
    public RespCustomerList getCustomerList() {

        RespCustomerList response = new RespCustomerList();

        try {
            LOGGER.info("Ip: " + Utility.getClientIp(request) + ", called getCustomerList");
            List<RespCustomer> respCustomerList = new ArrayList<>();
            List<Customer> customerList = customerDao.getCustomerList();

            if (customerList.isEmpty()) {
                response.setStatus(new RespStatus(ExceptionConstants.DATA_NOT_FOUND, "Data not found"));
                LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", Data not found");
                return response;
            }

            for (Customer customer : customerList) {
                RespCustomer respCustomer = new RespCustomer();
                respCustomer.setName(customer.getName());
                respCustomer.setSurname(customer.getSurname());
                respCustomer.setFname(customer.getFname());
                respCustomer.setMobile(customer.getMobile());
                respCustomer.setAddress(customer.getAddress());
                respCustomer.setGenderId(customer.getGenderId());
                respCustomer.setCustTypeId(customer.getCustTypeId());
                respCustomer.setDocumentTypeId(customer.getDocumentTypeId());
                respCustomerList.add(respCustomer);
                response.setCustomerList(respCustomerList);
                response.setStatus(RespStatus.getSuccessMessage());
            }
            LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", success");
        } catch (Exception e) {
            LOGGER.error("Ip: " + Utility.getClientIp(request) + ", error: " + e);
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            return response;
        }
        return response;
    }

    @Override
    public RespStatus createCustomer(ReqCustomer reqCustomer) {

        RespStatus response = new RespStatus();

        try {
            if ((reqCustomer.getName() == null || reqCustomer.getName().isEmpty())
                    || (reqCustomer.getSurname() == null || reqCustomer.getSurname().isEmpty())
                    || (reqCustomer.getFname() == null || reqCustomer.getFname().isEmpty())
                    || reqCustomer.getGenderId() == null || reqCustomer.getCustTypeId() == null) {
                response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                response.setStatusMessage("Invalid request data");
                return response;
            }
            if (reqCustomer.getCustTypeId().equals(EnumCustomerType.NOMINAL_CUSTOMER.getValue())) {
                if (reqCustomer.getMobile() == null || reqCustomer.getMobile().isEmpty()) {
                    response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                    response.setStatusMessage("Invalid request data");
                    return response;
                }
            }
            Customer customer = new Customer();
            customer.setName(reqCustomer.getName());
            customer.setSurname(reqCustomer.getSurname());
            customer.setFname(reqCustomer.getFname());
            customer.setMobile(reqCustomer.getMobile());
            customer.setMobile(reqCustomer.getMobile());
            customer.setAddress(reqCustomer.getAddress());
            customer.setGenderId(reqCustomer.getGenderId());
            customer.setCustTypeId(reqCustomer.getCustTypeId());
            customer.setDocumentTypeId(reqCustomer.getDocumentTypeId());
            customerDao.createCustomer(customer);
            response.setStatusCode(RespStatus.getSuccessMessage().getStatusCode());
            response.setStatusMessage(RespStatus.getSuccessMessage().getStatusMessage());
            LOGGER.warn("Ip: " + Utility.getClientIp(request) + "response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(ExceptionConstants.INTERNAL_EXCEPTION);
            response.setStatusMessage("Internal exception");
            return response;
        }
        return response;
    }

    @Override
    public RespStatus updateCustomer(ReqCustomer reqCustomer) {

        RespStatus response = new RespStatus();

        try {
            if (reqCustomer.getId() == null || (reqCustomer.getName() == null || reqCustomer.getName().isEmpty())
                    || (reqCustomer.getSurname() == null || reqCustomer.getSurname().isEmpty())
                    || (reqCustomer.getFname() == null || reqCustomer.getFname().isEmpty())
                    || reqCustomer.getGenderId() == null || reqCustomer.getCustTypeId() == null
                    || reqCustomer.getDocumentTypeId() == null) {
                response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                response.setStatusMessage("Invalid request data");
                return response;
            }
            if (reqCustomer.getCustTypeId().equals(EnumCustomerType.NOMINAL_CUSTOMER.getValue())) {
                if (reqCustomer.getMobile() == null || reqCustomer.getMobile().isEmpty()) {
                    response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                    response.setStatusMessage("Invalid request data");
                    return response;
                }
            }

            Customer customer = customerDao.getCustomerById(reqCustomer.getId());

            if (customer == null) {
                response.setStatusCode(ExceptionConstants.CUSTOMER_NOT_EXIST_AT_BANK);
                response.setStatusMessage("Customer not exist at bank");
                return response;
            }
            customer.setId(reqCustomer.getId());
            customer.setName(reqCustomer.getName());
            customer.setSurname(reqCustomer.getSurname());
            customer.setFname(reqCustomer.getFname());
            customer.setMobile(reqCustomer.getMobile());
            customer.setMobile(reqCustomer.getMobile());
            customer.setAddress(reqCustomer.getAddress());
            customer.setGenderId(reqCustomer.getGenderId());
            customer.setCustTypeId(reqCustomer.getCustTypeId());
            customer.setDocumentTypeId(reqCustomer.getDocumentTypeId());
            customerDao.updateCustomer(customer);
            response.setStatusCode(RespStatus.getSuccessMessage().getStatusCode());
            response.setStatusMessage(RespStatus.getSuccessMessage().getStatusMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(ExceptionConstants.INTERNAL_EXCEPTION);
            response.setStatusMessage("Internal exception");
            return response;
        }
        return response;
    }

    @Override
    public RespCustomer getCustomerById(Long custId) {

        RespCustomer response = new RespCustomer();

        try {
            LOGGER.info("Ip: " + Utility.getClientIp(request) + ", called getCustomerById, custId = " + custId);
            if (custId == null) {
                response.setStatus(new RespStatus(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data"));
                return response;
            }

            Customer customer = customerDao.getCustomerById(custId);
            if (customer == null) {
                response.setStatus(new RespStatus(ExceptionConstants.CUSTOMER_NOT_EXIST_AT_BANK, "Customer not exist at bank"));
                return response;
            }

            response.setId(customer.getId());
            response.setName(customer.getName());
            response.setSurname(customer.getSurname());
            response.setFname(customer.getFname());
            response.setMobile(customer.getMobile());
            response.setAddress(customer.getAddress());
            response.setGenderId(customer.getGenderId());
            response.setCustTypeId(customer.getCustTypeId());
            response.setDocumentTypeId(customer.getDocumentTypeId());
            response.setStatus(RespStatus.getSuccessMessage());
            LOGGER.warn("Ip: " + Utility.getClientIp(request) + ", success");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
            return response;
        }
        return response;
    }

    @Override
    public RespStatus deleteCustomer(Long custId) {

        RespStatus response = new RespStatus();

        try {

            if (custId == null) {
                response.setStatusCode(ExceptionConstants.INVALID_REQUEST_DATA);
                response.setStatusMessage("Invalid request data");
                return response;
            }

            Customer customer = customerDao.getCustomerById(custId);
            if (customer == null) {
                response.setStatusCode(ExceptionConstants.CUSTOMER_NOT_EXIST_AT_BANK);
                response.setStatusMessage("Card not found");
                return response;
            }
            customerDao.deleteCustomer(custId);
            response.setStatusCode(RespStatus.getSuccessMessage().getStatusCode());
            response.setStatusMessage(RespStatus.getSuccessMessage().getStatusMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(ExceptionConstants.INTERNAL_EXCEPTION);
            response.setStatusMessage("Internal exception");
            return response;
        }

        return response;
    }
}
