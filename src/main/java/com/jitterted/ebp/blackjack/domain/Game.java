package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleCard;
import com.jitterted.ebp.blackjack.adapter.in.console.ConsoleHand;

import static org.fusesource.jansi.Ansi.ansi;

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

    public void displayGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleHand.displayFaceUpCard(dealerHand));

        // second card is the hole card, which is hidden, or "face down"
        ConsoleCard.displayBackOfCard();

        System.out.println();
        System.out.println("Player has: ");
        System.out.println(ConsoleHand.cardsAsString(playerHand));
        System.out.println(" (" + playerHand.value() + ")");
    }

    public void displayFinalGameState() {
        System.out.print(ansi().eraseScreen().cursor(1, 1));
        System.out.println("Dealer has: ");
        System.out.println(ConsoleHand.cardsAsString(dealerHand));
        System.out.println(" (" + dealerHand.value() + ")");

        System.out.println();
        System.out.println("Player has: ");
        System.out.println(ConsoleHand.cardsAsString(playerHand));
        System.out.println(" (" + playerHand.value() + ")");
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

