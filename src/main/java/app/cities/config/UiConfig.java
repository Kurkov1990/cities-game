package app.cities.config;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

public final class UiConfig {

    private UiConfig() {}

    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 500;

    public static final int LEFT_WIDTH = 170;
    public static final int INPUT_HEIGHT = 30;
    public static final int BUTTON_HEIGHT = 38;
    public static final int ANSWER_HEIGHT = 250;

    public static final float PROMPT_FONT_SIZE = 12f;
    public static final float COMPUTER_FONT_SIZE = 12f;

    public static final int ROOT_PADDING = 14;
    public static final int GAP_6 = 6;
    public static final int GAP_10 = 10;
    public static final int GAP_12 = 12;
    public static final int GAP_14 = 14;
    public static final int GAP_18 = 18;
    public static final int RIGHT_INSET = 22;

    public static final String APP_TITLE = "Міста";

    public static void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 14));
            UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 14));
            UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 14));
            UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 14));
            UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.BOLD, 13));
        } catch (Exception ignored) {
        }
    }

    public static Image loadAppIcon() {
        URL url = UiConfig.class.getClassLoader().getResource("icon.png");
        if (url == null) {
            return null;
        }
        return new ImageIcon(url).getImage();
    }
}