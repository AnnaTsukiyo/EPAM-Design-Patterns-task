package com.epam.rd.autocode.startegy.cards;

import com.epam.rd.autocode.startegy.cards.strategies.BridgeCardDealingStrategy;
import com.epam.rd.autocode.startegy.cards.strategies.ClassicPokerCardDealingStrategy;
import com.epam.rd.autocode.startegy.cards.strategies.FoolCardDealingStrategy;
import com.epam.rd.autocode.startegy.cards.strategies.TexasHoldemCardDealingStrategy;

public class CardDealingStrategies {
    public static CardDealingStrategy texasHoldemCardDealingStrategy() {
        return new TexasHoldemCardDealingStrategy();
    }

    public static CardDealingStrategy classicPokerCardDealingStrategy() {
        return new ClassicPokerCardDealingStrategy();
    }

    public static CardDealingStrategy bridgeCardDealingStrategy() {
        return new BridgeCardDealingStrategy();
    }

    public static CardDealingStrategy foolCardDealingStrategy() {
        return new FoolCardDealingStrategy();
    }
}
