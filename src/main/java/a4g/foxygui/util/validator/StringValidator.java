package a4g.foxygui.util.validator;

import java.lang.String;

public final class StringValidator {
    private StringValidator(){};

    public static String NotEmpty(String string) throws IllegalArgumentException {
        if (ObjectValidator.NotNull(string).isEmpty())
            throw new IllegalArgumentException("String must not be empty");
        return string;
    }

    public static String Max(String string, int max) throws IllegalArgumentException {
        if (StringValidator.NotEmpty(string).length() > max)
            throw new IllegalArgumentException("String must have a maximum of " + max + "characters. " + string.length() + " provided.");
        return string;
    }

    public static String Min(String string, int min) throws IllegalArgumentException {
        if (StringValidator.NotEmpty(string).length() < min)
            throw new IllegalArgumentException("String must have a minimum of " + min + " characters. " + string.length() + " provided.");
        return string;
    }

    public static String Between(String string, int min, int max) throws IllegalArgumentException {
        if (StringValidator.NotEmpty(string).length() < min || string.length() > max)
            throw new IllegalArgumentException("String must have a minimum of " + min + " and a maximum of " + max + " characters. " + string.length() + " provided.");
        return string;
    }
}
