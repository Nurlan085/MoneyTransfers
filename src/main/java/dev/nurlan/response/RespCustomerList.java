package dev.nurlan.response;

import lombok.Data;

import java.util.List;

@Data
public class RespCustomerList {

    private List<RespCustomer> customerList;
    private RespStatus status;
}
