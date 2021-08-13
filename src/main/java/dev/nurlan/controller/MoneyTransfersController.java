package dev.nurlan.controller;


import dev.nurlan.request.ReqMoneyTransfers;
import dev.nurlan.response.RespMoneyTransfers;
import dev.nurlan.service.MoneyTransfersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/moneyTransfers")
public class MoneyTransfersController {


    @Autowired
    private MoneyTransfersService MoneyTransfersService;


    @PostMapping(value = "/cardToCard")
    public RespMoneyTransfers cardToCard(@RequestBody ReqMoneyTransfers reqMoneyTransfers) {
        return MoneyTransfersService.cardToCard(reqMoneyTransfers);
    }

}
