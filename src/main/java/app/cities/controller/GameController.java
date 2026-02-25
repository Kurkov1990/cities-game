package app.cities.controller;

import app.cities.config.Messages;
import app.cities.model.GameEngine;
import app.cities.model.GameResult;
import app.cities.model.GameState;
import app.cities.view.GameView;

public class GameController {

    private final GameView view;
    private final GameEngine engine;

    public GameController(GameView view, GameEngine engine) {
        this.view = view;
        this.engine = engine;

        view.onMove(e -> handleMove());
        view.onNewGame(e -> handleNewGame());
    }

    public void show() {
        view.setVisible(true);
        engine.newGame();

        view.resetUi();
        view.setComputerAnswerText(Messages.INITIAL_ANSWER_TEXT);
        view.setGameEnabled(true);

        syncViewFromState();
        view.focusInput();
    }

    private void handleMove() {
        String input = view.getInputText();
        String message = engine.makeHumanMove(input);

        view.setComputerAnswerText(message);
        view.clearInput();
        view.focusInput();

        syncViewFromState();

        GameState state = engine.state();
        if (state.result() != GameResult.IN_PROGRESS) {
            view.setGameEnabled(false);

            String header = switch (state.result()) {
                case HUMAN_WON -> "Вітаємо! Ви перемогли 🎉";
                case COMPUTER_WON -> "Гру завершено. Переміг комп’ютер 🤖";
                default -> "Гру завершено.";
            };

            view.showGameOverDialog(
                    Messages.gameOverDialogText(header, state.humanScore(), state.computerScore()),
                    Messages.GAME_OVER_TITLE
            );
        }
    }

    private void handleNewGame() {
        engine.newGame();

        view.resetUi();
        view.setComputerAnswerText(Messages.RESET_ANSWER_TEXT);
        view.setGameEnabled(true);

        syncViewFromState();
        view.focusInput();
    }

    private void syncViewFromState() {
        GameState state = engine.state();
        view.setScore(state.humanScore(), state.computerScore());

        String normalized = state.lastComputerCity();
        view.setComputerCityNormalized(normalized.isEmpty() ? null : normalized);
    }
}