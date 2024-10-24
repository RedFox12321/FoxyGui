package a4g.foxygui.util.validator;

import java.lang.Number;
import java.lang.String;


public final class NumberValidator {
    private NumberValidator(){}

    private final static String errorMsg = "Invalid numeric value. Value must be ";


    public static <T extends Number> T Positive(T number) throws IllegalArgumentException {
        if (number.doubleValue() <= 0)
            throw new IllegalArgumentException(errorMsg + "higher than 0.");
        return number;

    }
    public static <T extends Number> T PositiveOrZero(T number) throws IllegalArgumentException {
        if (number.doubleValue() < 0)
            throw new IllegalArgumentException(errorMsg + "equal or higher than 0.");
        return number;
    }

    public static <T extends Number> T Negative(T number) throws IllegalArgumentException {
        if (number.doubleValue() >= 0)
            throw new IllegalArgumentException(errorMsg + "lower than 0.");
        return number;
    }

    public static <T extends Number> T NegativeOrZero(T number) throws IllegalArgumentException {
        if (number.doubleValue() > 0)
            throw new IllegalArgumentException(errorMsg + "equal or lower than 0.");
        return number;
    }

    public static <T extends Number> T Max(T number, double max) throws IllegalArgumentException{
        if (number.doubleValue() > max)
            throw new IllegalArgumentException(errorMsg + "equal or lower than " + max);
        return number;
    }

    public static <T extends Number> T Min(T number, double min) throws IllegalArgumentException {
        if (number.doubleValue() < min)
            throw new IllegalArgumentException(errorMsg + "equal or higher than " + min);
        return number;

    }

    public static <T extends Number> T Range(T number, double min, double max) throws IllegalArgumentException {
        if (number.doubleValue() < min || number.doubleValue() > max)
            throw new IllegalArgumentException(errorMsg + "between " + min + " and " + max);
        return number;
    }
}
