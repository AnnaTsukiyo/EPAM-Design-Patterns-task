package com.epam.rd.autocode.startegy.cards.strategies;

import com.epam.rd.autocode.startegy.cards.Card;
import com.epam.rd.autocode.startegy.cards.CardDealingStrategy;
import com.epam.rd.autocode.startegy.cards.Deck;

import java.util.*;

public class FoolCardDealingStrategy implements CardDealingStrategy {
    private final static String STRING = "Player ";
    private final static String REMAINING = "Remaining";
    private Map<String, List<Card>> stacks;
    private final static int PLAYER_AMOUNT_CARDS = 6;
    private final static String TRUMP_CARD = "Trump card";

    @Override
    public Map<String, List<Card>> dealStacks(Deck deck, int players) {
        this.stacks = new TreeMap<>();
        addPlayerWithCards(deck, players);
        addTrumpCard(deck);
        addRemainingCards(deck);
        return stacks;
    }

    private void addPlayerWithCards(Deck deck, int players) {
        List<Card> cards = getCardsForPlayers(deck, players);
        for (int playerIndex = 0, mapIndex = 1; playerIndex < players; playerIndex++, mapIndex++) {
            List<Card> playerCards = getPlayerCards(cards, playerIndex, players);
            stacks.put(STRING + mapIndex, playerCards);
        }
    }

    private List<Card> getPlayerCards(List<Card> cards, int playerIndex, int players) {
        List<Card> playerCards = new ArrayList<>();
        for (int cardIndex = playerIndex, step = 0; cardIndex < cards.size(); cardIndex += players, step++)
            playerCards.add(cards.get(playerIndex + players * step));
        return playerCards;
    }

    private List<Card> getCardsForPlayers(Deck deck, int players) {
        List<Card> cards = new ArrayList<>();
        for (int index = 0; index < players * PLAYER_AMOUNT_CARDS; index++) cards.add(deck.dealCard());
        return cards;
    }

    private void addRemainingCards(Deck deck) {
        List<Card> remainingCards = new ArrayList<>();
        int remainingSize = deck.size();
        for (int dealIndex = 0; dealIndex < remainingSize; dealIndex++)
            remainingCards.add(deck.dealCard());
        stacks.put(REMAINING, remainingCards);
    }

    private void addTrumpCard(Deck deck) {
        stacks.put(TRUMP_CARD, Collections.singletonList(deck.dealCard()));
    }

    @Override
    public String toString() {
        return stacks.toString();
    }
}