package com.jitterted.ebp.blackjack.domain;

public class Game {

    private final Deck deck;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    private boolean playerDone = false;

    public Game() {
        deck = new Deck();
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public void determineOutcome() {
        if (playerHand.isBusted()) {
            System.out.println("You Busted, so you lose.  💸");
        } else if (dealerHand.isBusted()) {
            System.out.println("Dealer went BUST, Player wins! Yay for you!! 💵");
        } else if (playerHand.beats(dealerHand)) {
            System.out.println("You beat the Dealer! 💵");
        } else if (playerHand.pushes(dealerHand)) {
            System.out.println("Push: Nobody wins, we'll call it even.");
        } else {
            System.out.println("You lost to the Dealer. 💸");
        }
    }

    public void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }


    public Hand playerHand() {
        return playerHand;
    }

    // QUERY METHOD
    // -> Snapshot (point-in-time fixed)
    // -> Integrity (don't provide reference to internal field)
    // -> Immutable (snapshot, shouldn't be modified)
    //
    // OPTIONS
    // =NO=  1. clone - returns Hand -> misleading to clients, think they have the "real" Hand
    //          How to do this? How deep do we need to clone?
    // =NO=  2a. return DTO [ADAPTER ONLY] - returns HandDto -> getValue(), getCards(), isBusted()
    //
    // =YES= 2b. return Value Object [DOMAIN ONLY] - HandView -> value(), cards(), faceUpCard()
    //
    // =NO=  3. interface - ReadOnlyHand (QUERIES only) vs. DrawableHand (COMMANDS & QUERIES)
    //      not really immutable
    public Hand dealerHand() {
        return dealerHand;
    }


    public boolean isPlayerDone() {
        return playerDone;
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();
    }

    public void playerStands() {
        playerDone = true;
    }

}

