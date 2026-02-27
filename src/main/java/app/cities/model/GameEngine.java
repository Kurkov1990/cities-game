package app.cities.model;

import app.cities.config.Messages;
import app.cities.util.TextNormalizer;

import java.util.Optional;

public class GameEngine {

    private static final String SURRENDER = "здаюсь";

    private final CityRepository repo;
    private GameState state;

    public GameEngine(CityRepository repo) {
        this.repo = repo;
        this.state = new GameState();
    }

    public GameState getState() {
        return state;
    }

    public void newGame() {
        this.state = new GameState();
    }

    public String makeHumanMove(String inputRaw) {
        if (state.isFinished()) {
            return Messages.GAME_ALREADY_OVER;
        }

        String input = TextNormalizer.normalize(inputRaw);

        if (input.isBlank()) {
            return Messages.PLEASE_ENTER_CITY;
        }

        if (input.equals(SURRENDER)) {
            state.finish(GameResult.COMPUTER_WON);
            return Messages.SURRENDERED;
        }

        if (!repo.containsCity(input)) {
            return Messages.CITY_NOT_FOUND;
        }

        if (state.isUsed(input)) {
            return Messages.CITY_ALREADY_USED;
        }

        if (state.hasRequiredLetter() && input.charAt(0) != state.requiredFirstLetter()) {
            return Messages.WRONG_FIRST_LETTER_PREFIX
                    + state.requiredFirstLetter()
                    + Messages.WRONG_FIRST_LETTER_SUFFIX;
        }

        state.addUsed(input);
        state.lastHumanCity(input);
        state.incHumanScore();

        char last = TextNormalizer.lastMeaningfulLetter(input);
        if (last == 0) {
            state.finish(GameResult.COMPUTER_WON);
            return Messages.CANNOT_DETECT_LAST_LETTER;
        }

        Optional<String> answer = repo.pickAnswer(last, state.usedView());
        if (answer.isEmpty()) {
            state.finish(GameResult.HUMAN_WON);
            state.lastComputerCity("");
            return Messages.humanWonNoCity(last);
        }

        String computerCity = answer.get();
        state.addUsed(computerCity);
        state.lastComputerCity(computerCity);
        state.incComputerScore();

        char nextRequired = TextNormalizer.lastMeaningfulLetter(computerCity);
        if (nextRequired == 0) {
            state.finish(GameResult.HUMAN_WON);
            return Messages.computerMoveButYouWon(TextNormalizer.toDisplayName(computerCity));
        }

        state.setRequiredFirstLetter(nextRequired);
        return Messages.computerMoveAndNext(TextNormalizer.toDisplayName(computerCity), nextRequired);
    }
}