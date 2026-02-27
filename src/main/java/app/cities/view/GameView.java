package app.cities.view;

import app.cities.config.Messages;
import app.cities.config.UiConfig;
import app.cities.util.TextNormalizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameView extends JFrame {

    private final JTextField input = new JTextField();
    private final JButton moveButton = new JButton(Messages.MOVE_BUTTON_TEXT);
    private final JButton newGameButton = new JButton(Messages.NEW_GAME_BUTTON_TEXT);

    private final JLabel promptLabel = new JLabel(Messages.PROMPT_TEXT);
    private final JLabel computerLabel = new JLabel(Messages.COMPUTER_PREFIX + Messages.DASH);

    private final JTextArea computerAnswer = new JTextArea();
    private final JLabel scoreLabel = new JLabel(defaultScoreText());

    public GameView(Image icon) {
        super(UiConfig.APP_TITLE);

        if (icon != null) {
            setIconImage(icon);
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(UiConfig.WINDOW_WIDTH, UiConfig.WINDOW_HEIGHT);
        setMinimumSize(new Dimension(UiConfig.WINDOW_WIDTH, UiConfig.WINDOW_HEIGHT));

        JPanel root = new JPanel();
        root.setBorder(BorderFactory.createEmptyBorder(
                UiConfig.ROOT_PADDING, UiConfig.ROOT_PADDING, UiConfig.ROOT_PADDING, UiConfig.ROOT_PADDING
        ));
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        setContentPane(root);

        root.add(buildTopPanel());

        root.add(Box.createVerticalStrut(UiConfig.GAP_10));
        root.add(buildActionsPanel());

        root.add(Box.createVerticalStrut(UiConfig.GAP_14));
        root.add(buildAnswerTitle());

        root.add(Box.createVerticalStrut(UiConfig.GAP_6));
        root.add(buildAnswerScroll());

        root.add(Box.createVerticalStrut(UiConfig.GAP_12));
        scoreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        root.add(scoreLabel);

        setLocationRelativeTo(null);

        resetUi();
    }

    public String getInputText() {
        return input.getText();
    }

    public void clearInput() {
        input.setText("");
    }

    public void focusInput() {
        input.requestFocusInWindow();
    }

    public void setComputerAnswerText(String text) {
        computerAnswer.setText(text);
        computerAnswer.setCaretPosition(0);
    }

    public void setComputerCityNormalized(String normalizedCity) {
        String display = TextNormalizer.toDisplayName(normalizedCity);
        computerLabel.setText(Messages.COMPUTER_PREFIX + (display == null ? Messages.DASH : display));
    }

    public void setScore(int human, int computer) {
        scoreLabel.setText(Messages.SCORE_TEMPLATE_PREFIX + human + " — " + computer + Messages.SCORE_TEMPLATE_SUFFIX);
    }

    public void resetUi() {
        clearInput();
        setComputerCityNormalized(null);
        input.setToolTipText(Messages.INPUT_TOOLTIP);
        focusInput();
    }

    public void setGameEnabled(boolean enabled) {
        input.setEnabled(enabled);
        moveButton.setEnabled(enabled);
    }

    public void onMove(ActionListener l) {
        moveButton.addActionListener(l);
        input.addActionListener(l);
    }

    public void onNewGame(ActionListener l) {
        newGameButton.addActionListener(l);
    }

    public void showGameOverDialog(String message, String title) {
        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JPanel buildTopPanel() {
        JPanel top = new JPanel(new GridBagLayout());
        top.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        Dimension inputSize = new Dimension(UiConfig.LEFT_WIDTH, UiConfig.INPUT_HEIGHT);
        input.setPreferredSize(inputSize);
        input.setMinimumSize(inputSize);
        input.setMaximumSize(inputSize);
        input.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension buttonSize = new Dimension(UiConfig.LEFT_WIDTH, UiConfig.BUTTON_HEIGHT);
        moveButton.setPreferredSize(buttonSize);
        moveButton.setMinimumSize(buttonSize);
        moveButton.setMaximumSize(buttonSize);
        moveButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        left.add(input);
        left.add(Box.createVerticalStrut(UiConfig.GAP_10));
        left.add(moveButton);

        left.setMinimumSize(new Dimension(UiConfig.LEFT_WIDTH, UiConfig.INPUT_HEIGHT + UiConfig.GAP_10 + UiConfig.BUTTON_HEIGHT));
        left.setMaximumSize(new Dimension(UiConfig.LEFT_WIDTH, Integer.MAX_VALUE));

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        promptLabel.setFont(promptLabel.getFont().deriveFont(Font.BOLD, UiConfig.PROMPT_FONT_SIZE));
        computerLabel.setFont(computerLabel.getFont().deriveFont(Font.BOLD, UiConfig.COMPUTER_FONT_SIZE));

        right.add(promptLabel);
        right.add(Box.createVerticalStrut(UiConfig.GAP_18));
        right.add(computerLabel);

        GridBagConstraints cLeft = new GridBagConstraints();
        cLeft.gridx = 0;
        cLeft.gridy = 0;
        cLeft.anchor = GridBagConstraints.NORTHWEST;
        cLeft.fill = GridBagConstraints.VERTICAL;
        cLeft.weightx = 0;
        cLeft.weighty = 0;
        cLeft.insets = new Insets(0, 0, 0, 0);
        top.add(left, cLeft);

        GridBagConstraints cRight = new GridBagConstraints();
        cRight.gridx = 1;
        cRight.gridy = 0;
        cRight.anchor = GridBagConstraints.NORTHWEST;
        cRight.fill = GridBagConstraints.HORIZONTAL;
        cRight.weightx = 1;
        cRight.weighty = 0;
        cRight.insets = new Insets(0, UiConfig.RIGHT_INSET, 0, 0);
        top.add(right, cRight);

        return top;
    }

    private JPanel buildActionsPanel() {
        JPanel actions = new JPanel();
        actions.setLayout(new BoxLayout(actions, BoxLayout.X_AXIS));
        actions.setAlignmentX(Component.LEFT_ALIGNMENT);
        actions.add(Box.createHorizontalGlue());
        actions.add(newGameButton);
        return actions;
    }

    private JLabel buildAnswerTitle() {
        JLabel answerTitle = new JLabel(Messages.ANSWER_TITLE);
        answerTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        return answerTitle;
    }

    private JScrollPane buildAnswerScroll() {
        computerAnswer.setText(Messages.INITIAL_ANSWER_TEXT);
        computerAnswer.setEditable(false);
        computerAnswer.setFocusable(false);
        computerAnswer.setLineWrap(true);
        computerAnswer.setWrapStyleWord(true);

        JScrollPane answerScroll = new JScrollPane(computerAnswer);
        answerScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        answerScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        answerScroll.setPreferredSize(new Dimension(0, UiConfig.ANSWER_HEIGHT));
        answerScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, UiConfig.ANSWER_HEIGHT));
        return answerScroll;
    }

    private static String defaultScoreText() {
        return Messages.SCORE_TEMPLATE_PREFIX + "0 — 0" + Messages.SCORE_TEMPLATE_SUFFIX;
    }
}