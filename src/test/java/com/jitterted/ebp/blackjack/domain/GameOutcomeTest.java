package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    public void playerHitsAndGoesBustAndLoses() throws Exception {
        Game game = new Game(StubDeck.playerHitsAndGoesBust());
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
    }

    @Test
    public void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() throws Exception {
        Game game = new Game(StubDeck.playerStandsAndBeatsDealer());
        game.initialDeal();

        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    public void newGameThenPlayerIsNotDone() throws Exception {
        Game game = new Game(StubDeck.playerNotDealtBlackjack());

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    public void initialDealNoBlackjackDealtThenPlayerIsNotDone() throws Exception {
        Game game = new Game(StubDeck.playerNotDealtBlackjack());

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    public void playerDealtBlackjackUponInitialDealThenWinsBlackjackAndIsDone() throws Exception {
        Game game = new Game(StubDeck.playerDealtBlackjackDealerNotBlackjack());

        game.initialDeal();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_WINS_BLACKJACK);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    public void standResultsInDealerDrawingCardOnTheirTurn() throws Exception {
        Game game = new Game(StubDeck.dealerDrawsAdditionalCardAfterPlayerStands());
        game.initialDeal();

        game.playerStands();

        assertThat(game.dealerHand().cards())
                .hasSize(3);
    }



}