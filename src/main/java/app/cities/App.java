package app.cities;

import app.cities.config.UiConfig;
import app.cities.controller.GameController;
import app.cities.controller.WelcomeController;
import app.cities.model.CityRepository;
import app.cities.model.GameEngine;
import app.cities.util.CitiesDataLoadException;
import app.cities.view.GameView;
import app.cities.view.WelcomeView;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Image;

public class App {

    public static void main(String[] args) {
        UiConfig.setupLookAndFeel();
        Image icon = UiConfig.loadAppIcon();

        final CityRepository repo;
        try {
            repo = CityRepository.fromResource("cities_ua.txt");
        } catch (CitiesDataLoadException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
                    null,
                    "Не вдалося завантажити список міст.\n" + e.getMessage(),
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE
            ));
            return;
        }

        GameEngine engine = new GameEngine(repo);

        SwingUtilities.invokeLater(() -> {
            WelcomeView welcomeView = new WelcomeView(icon);
            new WelcomeController(
                    welcomeView,
                    () -> {
                        GameView gameView = new GameView(icon);
                        new GameController(gameView, engine).show();
                    }
            ).show();
        });
    }
}