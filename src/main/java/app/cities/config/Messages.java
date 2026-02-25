
package app.cities.config;

public final class Messages {

    private Messages() {}

    public static final String MOVE_BUTTON_TEXT = "Зробити хід";
    public static final String NEW_GAME_BUTTON_TEXT = "Нова гра";

    public static final String WELCOME_TITLE = "Вітаємо";
    public static final String WELCOME_HTML =
            "<html>Вітаємо вас у грі дитинства<br>і всіх розумників!</html>";
    public static final String WELCOME_OK = "ОК";

    public static final String PROMPT_TEXT = "Введіть назву міста";
    public static final String COMPUTER_PREFIX = "Комп'ютер: ";
    public static final String DASH = "—";
    public static final String ANSWER_TITLE = "Відповідь комп’ютера:";

    public static final String INPUT_TOOLTIP = "Наприклад: Київ (або «здаюсь»)";

    public static final String INITIAL_ANSWER_TEXT =
            "Введіть перше місто та натисніть «Зробити хід».";
    public static final String RESET_ANSWER_TEXT =
            "Нова гра!\nВведіть перше місто та натисніть «Зробити хід».";
    public static final String SCORE_TEMPLATE_PREFIX = "Рахунок: Ви ";
    public static final String SCORE_TEMPLATE_SUFFIX = " Комп’ютер";

    public static final String GAME_OVER_TITLE = "Гру завершено";

    public static final String GAME_ALREADY_OVER = "Гра вже завершена.";
    public static final String PLEASE_ENTER_CITY = "Введи назву міста.";
    public static final String SURRENDERED = "Ви написали «здаюсь». Комп’ютер перемагає.";
    public static final String CITY_NOT_FOUND = "Такого міста немає в базі.";
    public static final String CITY_ALREADY_USED = "Це місто вже використовувалось. Повторювати не можна.";
    public static final String WRONG_FIRST_LETTER_PREFIX = "Потрібно місто на літеру: «";
    public static final String WRONG_FIRST_LETTER_SUFFIX = "».";
    public static final String CANNOT_DETECT_LAST_LETTER = "Не вдалося визначити останню літеру. Гру завершено.";

    public static String humanWonNoCity(char lastLetter) {
        return "Я не можу знайти місто на «" + lastLetter + "». Ви перемогли!";
    }

    public static String computerMoveAndNext(String computerCityDisplay, char nextRequired) {
        return "Мій хід: " + computerCityDisplay + ". Тепер ваше місто на «" + nextRequired + "».";
    }

    public static String computerMoveButYouWon(String computerCityDisplay) {
        return "Мій хід: " + computerCityDisplay + ". Ви перемогли!";
    }

    public static String gameOverDialogText(String header, int human, int computer) {
        return header + "\n\nФінальний рахунок: Ви " + human + " — " + computer + " Комп’ютер";
    }
}