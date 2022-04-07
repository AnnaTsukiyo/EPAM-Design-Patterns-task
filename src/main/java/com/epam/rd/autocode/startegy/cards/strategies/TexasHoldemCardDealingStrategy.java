package com.epam.rd.autocode.startegy.cards.strategies;

import com.epam.rd.autocode.startegy.cards.Card;
import com.epam.rd.autocode.startegy.cards.CardDealingStrategy;
import com.epam.rd.autocode.startegy.cards.Deck;

import java.util.*;

public class TexasHoldemCardDealingStrategy implements CardDealingStrategy {
    private final static String COMMUNITY = "Community";
    private final static String STRING = "Player ";
    private final static String REMAINING = "Remaining";
    private Map<String, List<Card>> stack;
    private final static int PLAYER_AMOUNT_CARDS = 2;
    private final static int COMMUNITY_AMOUNT_CARDS = 5;

    @Override
    public Map<String, List<Card>> dealStacks(Deck deck, int players) {
        stack = new TreeMap<>();
        addPlayerWithCards(deck, players);
        addCommunityWithCards(deck);
        addRemainingCards(deck);
        return stack;
    }

    private void addCommunityWithCards(Deck deck) {
        List<Card> communityWithCards = new ArrayList<>();
        for (int dealIndex = 0; dealIndex < COMMUNITY_AMOUNT_CARDS; dealIndex++) communityWithCards.add(deck.dealCard());
        stack.put(COMMUNITY, communityWithCards);
    }

    private void addPlayerWithCards(Deck deck, int players) {
        List<Card> cards = getCardsForPlayers(deck, players);
        for (int playerIndex = 0; playerIndex < players; playerIndex++) {
            List<Card> playerCards = getPlayerCards(cards, playerIndex, players);
            stack.put(STRING + (playerIndex + 1), playerCards);
        }
    }

    private List<Card> getPlayerCards(List<Card> cards, int playerIndex, int players) {
        List<Card> playerCards = null;
        for (int cardIndex = playerIndex; cardIndex < cards.size(); cardIndex += players)
            playerCards = Arrays.asList(cards.get(playerIndex), cards.get(cardIndex));
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