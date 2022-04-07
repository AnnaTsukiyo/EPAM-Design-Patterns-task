package com.epam.rd.autocode.startegy.cards.strategies;

import com.epam.rd.autocode.startegy.cards.Card;
import com.epam.rd.autocode.startegy.cards.CardDealingStrategy;
import com.epam.rd.autocode.startegy.cards.Deck;

import java.util.*;

public class ClassicPokerCardDealingStrategy implements CardDealingStrategy {
    private final static String STRING = "Player ";
    private final static String REMAINING = "Remaining";
    private Map<String, List<Card>> stack;
    private final static int PLAYER_AMOUNT_CARDS = 5;

    @Override
    public Map<String, List<Card>> dealStacks(Deck deck, int players) {
        stack = new TreeMap<>();
        addPlayerWithCards(deck, players);
        addRemainingCards(deck);
        return stack;
    }

    private void addPlayerWithCards(Deck deck, int players) {
        List<Card> cards = getCardsForPlayers(deck, players);
        for (int playerIndex = 0, mapIndex = 1; playerIndex < players; playerIndex++, mapIndex++) {
            List<Card> playerCards = getPlayerCards(cards, playerIndex, players);
            stack.put(STRING + mapIndex, playerCards);
        }
    }

    private List<Card> getPlayerCards(List<Card> cards, int playerIndex, int players) {
        List<Card> playerCards = new ArrayList<>();
        for (int cardIndex = playerIndex, step = 0; cardIndex < cards.size(); cardIndex += players, step++)
            playerCards.add(cards.get(playerIndex + players * step));
        return playerCards;
    }

    private List<Card> getCardsForPlayers(Deck deck, int players) {
        List<Card> cards = new LinkedList<>();
        for (int index = 0; index < players * PLAYER_AMOUNT_CARDS; index++) cards.add(deck.dealCard());
        return cards;
    }

    private void addRemainingCards(Deck deck) {
        List<Card> communityWithCards = new ArrayList<>();
        int remainingSize = deck.size();
        for (int dealIndex = 0; dealIndex < remainingSize; dealIndex++)
            communityWithCards.add(deck.dealCard());
        stack.put(REMAINING, communityWithCards);
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}