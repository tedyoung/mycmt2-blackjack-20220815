package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public Hand() {
    }

    public Hand(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public int value() {
        int handValue = cards
                .stream()
                .mapToInt(Card::rankValue)
                .sum();

        // does the hand contain at least 1 Ace?
        boolean hasAce = cards
                .stream()
                .anyMatch(card -> card.rankValue() == 1);

        // if the total hand value <= 11, then count the Ace as 11 by adding 10
        if (hasAce && handValue <= 11) {
            handValue += 10;
        }

        return handValue;
    }

    public Card dealerFaceUpCard() {
        return cards.get(0);
    }

    boolean dealerMustDrawCard() {
        return value() <= 16;
    }

    // can't return direct access to internal field (cards)
    // Options:
    // 1) Return copy -- implies a regular List
    // 2) Return Stream<Card> -- can only be "consumed" once
    public List<Card> cards() {
        return List.copyOf(cards);
    }

    public void drawFrom(Deck deck) {
        // Invariant: not allowed if Hand is in a "terminal" state: busted or has Blackjack
        cards.add(deck.draw());
    }

    boolean isBusted() {
        return value() > 21;
    }

    boolean pushes(Hand hand) {
        return hand.value() == value();
    }

    boolean beats(Hand hand) {
        return hand.value() < value();
    }

    boolean isBlackjack() {
        return value() == 21 && cards.size() == 2;
    }
}
