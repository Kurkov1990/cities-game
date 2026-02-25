package app.cities.controller;

import app.cities.view.WelcomeView;

import javax.swing.SwingUtilities;

public class WelcomeController {
    private final WelcomeView welcomeView;
    private final Runnable onStart;

    public WelcomeController(WelcomeView welcomeView, Runnable onStart) {
        this.welcomeView = welcomeView;
        this.onStart = onStart;

        welcomeView.onStart(e -> {
            welcomeView.setVisible(false);
            onStart.run();
        });
    }

    public void show() {
        SwingUtilities.invokeLater(() -> welcomeView.setVisible(true));
    }
}