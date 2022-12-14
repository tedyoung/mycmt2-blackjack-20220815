package com.jitterted.ebp.blackjack.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StubDeck extends Deck {
    private static final Suit DUMMY_SUIT = Suit.HEARTS;
    private final ListIterator<Card> iterator;

    public StubDeck(List<Card> cards) {
        this.iterator = cards.listIterator();
    }

    public StubDeck(Rank... ranks) {
        List<Card> cards = new ArrayList<>();
        for (Rank rank : ranks) {
            cards.add(new Card(DUMMY_SUIT, rank));
        }
        this.iterator = cards.listIterator();
    }

    public static Deck playerHitsAndDoesNotGoBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.SEVEN, Rank.JACK,
                            Rank.THREE);
    }

    public static Deck playerHitsAndGoesBust() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK,
                            Rank.THREE);
    }

    public static Deck playerStandsAndBeatsDealer() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.QUEEN, Rank.JACK);
    }

    public static Deck playerNotDealtBlackjack() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.NINE, Rank.JACK);
    }

    public static Deck playerDealtBlackjackDealerNotBlackjack() {
        return new StubDeck(Rank.TEN, Rank.EIGHT,
                            Rank.ACE, Rank.JACK);
    }

    public static StubDeck dealerDrawsAdditionalCardAfterPlayerStands() {
        return new StubDeck(Rank.TEN, Rank.QUEEN,
                            Rank.NINE, Rank.FIVE,
                            Rank.SIX);
    }

    @Override
    public Card draw() {
        return iterator.next();
    }
}
