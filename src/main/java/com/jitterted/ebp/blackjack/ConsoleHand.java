package com.jitterted.ebp.blackjack;

public class ConsoleHand {
    // Translate DOMAIN OBJECT (Hand) --> Some String for display purposes
    static String displayFaceUpCard(Hand hand) {
        return ConsoleCard.display(hand.dealerFaceUpCard());
    }
}
