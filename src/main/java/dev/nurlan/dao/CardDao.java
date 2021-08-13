package dev.nurlan.dao;

import dev.nurlan.model.Card;

import java.util.List;

public interface CardDao {

    void createCard(Card card) throws Exception;

    void deleteCard(Long cardId) throws Exception;

    Card getCardById(Long cardId) throws Exception;

    List<Card> getCardList() throws Exception;

    void updateCardBalance(Card card) throws Exception;

}
