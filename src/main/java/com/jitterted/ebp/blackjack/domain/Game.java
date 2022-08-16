package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;

public class Game {

    private final Deck deck;
    private final GameMonitor gameMonitor;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    private boolean playerDone = false;

    public Game(Deck deck) {
        this.deck = deck;
        this.gameMonitor = game -> {}; // Null Object Pattern
    }

    public Game(Deck deck, GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameMonitor = gameMonitor;
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    // String here is suspect: may be violating separation of concerns (and in fact, it does)
    public GameOutcome determineOutcome() {
        if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.isBlackjack()) {
            return GameOutcome.PLAYER_WINS_BLACKJACK;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_BEATS_DEALER;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES;
        } else {
            return GameOutcome.PLAYER_LOSES;
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

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
        updatePlayerDoneTo(playerHand.isBlackjack());
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        updatePlayerDoneTo(playerHand.isBusted());
    }

    public void playerStands() {
        dealerTurn();
        updatePlayerDoneTo(true);
    }

    private void updatePlayerDoneTo(boolean isPlayerDone) {
        playerDone = isPlayerDone;
        if (playerDone) {
            gameMonitor.roundCompleted(this);
        }
    }

}

