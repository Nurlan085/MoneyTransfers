package dev.nurlan.controller;

import dev.nurlan.request.ReqCard;
import dev.nurlan.response.RespCard;
import dev.nurlan.response.RespCardList;
import dev.nurlan.response.RespStatus;
import dev.nurlan.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/card")
public class CardController {

    @Autowired
    private CardService cardService;


    @GetMapping(value = "/getCardList")
    public RespCardList getCardList() {
        return cardService.getCardList();
    }

    @PostMapping(value = "/createCard")
    public RespStatus createCard(@RequestBody ReqCard reqCard) {
        return cardService.createCard(reqCard);
    }

    @PostMapping(value = "/increaseCardBalance")
    public RespStatus increaseCardBalance(@RequestBody ReqCard reqCard) {
        return cardService.increaseCardBalance(reqCard);
    }

    @RequestMapping(value = "/getCardById", method = {RequestMethod.GET, RequestMethod.POST})
    public RespCard getCardById(@RequestBody ReqCard reqCard) {
        return cardService.getCardById(reqCard);
    }


    @PostMapping(value = "/deleteCard")
    public RespStatus deleteCard(@RequestBody ReqCard reqCard) {
        return cardService.deleteCard(reqCard);
    }

}
