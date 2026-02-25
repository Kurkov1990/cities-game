package app.cities.util;

public class CitiesDataLoadException extends RuntimeException {
    public CitiesDataLoadException(String message) {
        super(message);
    }

    public CitiesDataLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
