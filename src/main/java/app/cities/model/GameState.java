package app.cities.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GameState {

    private final Set<String> used = new HashSet<>();

    private int humanScore;
    private int computerScore;

    private String lastHumanCity = "";
    private String lastComputerCity = "";

    private char requiredFirstLetter = 0;

    private GamePhase phase = GamePhase.NOT_STARTED;
    private GameResult result = GameResult.IN_PROGRESS;


    public Set<String> usedView() {
        return Collections.unmodifiableSet(used);
    }

    public boolean isUsed(String normalizedCity) {
        return used.contains(normalizedCity);
    }

    public void addUsed(String normalizedCity) {
        used.add(normalizedCity);
    }


    public int humanScore() {
        return humanScore;
    }

    public int computerScore() {
        return computerScore;
    }

    public void incHumanScore() {
        humanScore++;
    }

    public void incComputerScore() {
        computerScore++;
    }


    public boolean hasLastHumanCity() {
        return !lastHumanCity.isEmpty();
    }

    public String lastHumanCity() {
        return lastHumanCity;
    }

    public void lastHumanCity(String normalizedCity) {
        this.lastHumanCity = normalizedCity == null ? "" : normalizedCity;
        if (phase == GamePhase.NOT_STARTED) {
            phase = GamePhase.IN_PROGRESS;
        }
    }

    public boolean hasLastComputerCity() {
        return !lastComputerCity.isEmpty();
    }

    public String lastComputerCity() {
        return lastComputerCity;
    }

    public void lastComputerCity(String normalizedCity) {
        this.lastComputerCity = normalizedCity == null ? "" : normalizedCity;
    }

    public boolean hasRequiredLetter() {
        return requiredFirstLetter != 0;
    }

    public char requiredFirstLetter() {
        return requiredFirstLetter;
    }

    public void setRequiredFirstLetter(char c) {
        this.requiredFirstLetter = c;
    }

    public void clearRequiredFirstLetter() {
        this.requiredFirstLetter = 0;
    }

    public GamePhase phase() {
        return phase;
    }

    public boolean isFinished() {
        return phase == GamePhase.FINISHED;
    }

    public GameResult result() {
        return result;
    }

    public void finish(GameResult result) {
        this.result = result;
        this.phase = GamePhase.FINISHED;
        clearRequiredFirstLetter();
    }
}