package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Game;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleGame {

    private final Game game;

    public ConsoleGame(Game game) {
        this.game = game;
    }

    public static void resetScreen() {
        System.out.println(ansi().reset());
    }

    public static void waitForEnterFromUser() {
        System.out.println(ansi()
                                   .cursor(3, 1)
                                   .fgBrightBlack().a("Hit [ENTER] to start..."));

        System.console().readLine();
    }

    public static void displayWelcomeScreen() {
        AnsiConsole.systemInstall();
        System.out.println(ansi()
                                   .bgBright(Ansi.Color.WHITE)
                                   .eraseScreen()
                                   .cursor(1, 1)
                                   .fgGreen().a("Welcome to")
                                   .fgRed().a(" JitterTed's")
                                   .fgBlack().a(" BlackJack game"));
    }

    public static String inputFromPlayer() {
        System.out.println("[H]it or [S]tand?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void start() {
        displayWelcomeScreen();
        waitForEnterFromUser();

        game.initialDeal();

        playerPlays();

        game.dealerTurn();

        game.displayFinalGameState();

        game.determineOutcome();

        resetScreen();
    }

    public void playerPlays() {
        while (!game.isPlayerDone()) {
            game.displayGameState();
            String command = inputFromPlayer();
            handle(command);
        }
    }

    public void handle(String command) {
        if (command.toLowerCase().startsWith("h")) {
            game.playerHits();
        } else if (command.toLowerCase().startsWith("s")) {
            game.playerStands();
        }
    }

}
