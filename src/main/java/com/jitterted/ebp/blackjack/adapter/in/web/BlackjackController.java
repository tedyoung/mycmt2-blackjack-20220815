package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// ADAPTER Controller
@Controller
public class BlackjackController {

    private Game game;

    @Autowired
    public BlackjackController(Game game) {
        this.game = game;
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        addGameViewTo(model);
        return "blackjack";
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        addGameViewTo(model);
        model.addAttribute("outcome", game.determineOutcome().display());
        return "done";
    }

    @PostMapping("/start-game")
    public String startGame() {
        game.initialDeal();
        return redirectBasedOnGameState();
    }

    @PostMapping("/hit")
    public String hitCommand() {
        game.playerHits();
        return redirectBasedOnGameState();
    }

    @PostMapping("/stand")
    public String standCommand() {
        game.playerStands();
        game.dealerTurn();
        return redirectBasedOnGameState();
    }

    // TRANSFORM GAME STATE into a REDIRECT
    private String redirectBasedOnGameState() {
        if (game.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

    private void addGameViewTo(Model model) {
        model.addAttribute("gameView", GameView.of(game));
    }



}
