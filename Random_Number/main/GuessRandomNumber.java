package com.luv2code.springboot.demo.mycoolapp.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class GuessRandomNumber {
    private int secret_number = 0;
    private boolean game_is_active = false;
    private int chances = 0;

    @GetMapping("/start")
    String beginning_message() {
        return "Welcome! <br> " +
                "Guess the natural number in the range [1, 100]. <br> " +
                "Select the mode by writing http://localhost:8080/mode?mode=... " +
                "and replace .. with a difficulty level(hard, easy, medium). <br>" +
                "Then you will have 15(easy), 10(medium) or 5(hard) chances. <br>" +
                "In order to take a guess, write http://localhost:8080/guess?guess=... and replace" +
                " ... with a number(your guess). <br>" +
                "In order to verify if a game is already started, go to /active. <br>";
    }


    @GetMapping("/mode")
    String get_mode(@RequestParam String mode) {
        Random random = new Random();
        secret_number = random.nextInt() % 100 + 1;
        if(mode.equalsIgnoreCase(("easy"))) {
            game_is_active = true;
            chances = 15;
            return "Game started. \n Difficulty : easy. \n You have 15 chances.";
        }
        if(mode.equalsIgnoreCase("medium")) {
            game_is_active = true;
            chances = 10;
            return "Game started. \n Difficulty : medium. \n You have 10 chances.";
        }
        if(mode.equalsIgnoreCase("hard" )) {
            game_is_active = true;
            chances = 5;
            return "Game started. \n Difficulty : hard. \n You have 5 chances.";
        }
        game_is_active = false;
        chances = 0;
        return "Difficulty level not valid!";
    }

    @GetMapping("/guess")
    String guess_number(@RequestParam int guess) {
        if(guess < 1 || guess > 100) {
            return "Invalid number. You have " + chances + " chances remained.";
        }
        if(!game_is_active) {
            return "No active game!";
        }
        chances--;
        if(guess == secret_number) {
            game_is_active = false;
            return "You guessed the number. Well done!";
        }
        if(chances == 0) {
            game_is_active = false;
            return "You lost! The number was" + secret_number;
        }
        return "Incorrect! Take another guess.. You have " + chances + " chances remained.";
    }

    @GetMapping("/active")
    String game_is_active() {
        if(game_is_active) {
            return "Game is active at the moment. You have " + chances + " chances remained.";
        }
        return "No active games";
    }
}
