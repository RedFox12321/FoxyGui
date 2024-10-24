package a4g.foxygui.util.validator;

public final class ObjectValidator {
    private ObjectValidator(){};

    public static <T> T NotNull(T object) throws IllegalArgumentException {
        if (object == null)
            throw new IllegalArgumentException("Object must not be null");
        return object;
    }
}
