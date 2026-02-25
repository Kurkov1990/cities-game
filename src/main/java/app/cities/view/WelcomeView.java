package app.cities.view;

import app.cities.config.Messages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class WelcomeView extends JFrame {

    private final JButton startButton = new JButton(Messages.WELCOME_OK);

    public WelcomeView(Image icon) {
        super(Messages.WELCOME_TITLE);

        if (icon != null) setIconImage(icon);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 120);
        setResizable(false);

        JLabel label = new JLabel(Messages.WELCOME_HTML, SwingConstants.LEFT);

        startButton.setPreferredSize(new Dimension(60, 28));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
        content.setBorder(new EmptyBorder(20, 15, 20, 15));

        content.add(label);
        content.add(Box.createHorizontalGlue());
        content.add(startButton);

        setContentPane(content);
        setLocationRelativeTo(null);
    }

    public void onStart(ActionListener listener) {
        startButton.addActionListener(listener);
    }
}