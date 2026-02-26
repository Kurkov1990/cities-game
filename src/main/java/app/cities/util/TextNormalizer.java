package app.cities.util;

import java.util.Locale;
import java.util.Set;

public final class TextNormalizer {

    private static final Locale UA = Locale.forLanguageTag("uk-UA");

    private static final Set<Character> SEPARATORS = Set.of(' ', '-', '\'');
    private static final Set<Character> TRAILING_IGNORED_LETTERS = Set.of('ь', 'й', 'и');

    private TextNormalizer() {
    }

    public static String normalize(String input) {
        if (input == null) {
            return null;
        }

        String s = input.trim();

        s = s.replace('’', '\'')
                .replace('ʼ', '\'');

        s = s.replace('–', '-')
                .replace('—', '-');

        s = trimPunctuationEdges(s);
        s = s.replaceAll("\\s+", " ");
        s = s.toLowerCase(UA);

        return s;
    }

    public static char lastMeaningfulLetter(String normalized) {
        if (normalized == null || normalized.isBlank()) {
            return 0;
        }

        for (int i = normalized.length() - 1; i >= 0; i--) {
            char c = normalized.charAt(i);

            if (isMeaningfulLetter(c)) {
                return c;
            }
        }

        return 0;
    }

    public static String toDisplayName(String normalizedCity) {
        String s = normalizedCity == null
                ? ""
                : normalizedCity.trim();

        if (s.isEmpty() || s.equals("0")) {
            return null;
        }

        return s.substring(0, 1).toUpperCase(UA) + s.substring(1);
    }

    private static boolean isMeaningfulLetter(char c) {
        return Character.isLetter(c)
                && !SEPARATORS.contains(c)
                && !TRAILING_IGNORED_LETTERS.contains(c);
    }

    private static String trimPunctuationEdges(String s) {
        int start = 0;
        int end = s.length();

        while (start < end && isEdgeGarbage(s.charAt(start))) {
            start++;
        }

        while (end > start && isEdgeGarbage(s.charAt(end - 1))) {
            end--;
        }

        return s.substring(start, end);
    }

    private static boolean isEdgeGarbage(char c) {
        return !Character.isLetter(c) && !SEPARATORS.contains(c);
    }
}