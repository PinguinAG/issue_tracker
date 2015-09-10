package ebuero.aatasiei.tracker.utils;

import com.google.common.base.Preconditions;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.nullToEmpty;
import static java.util.Objects.nonNull;

/**
 * @author aatasiei
 * @version 1.0
 * @since 25-iul.-2015
 */
public final class MorePreconditions {

    private MorePreconditions() {
        // utility class
    }

    /**
     * Checks that the argument is not null. Otherwise it throws an {@link IllegalArgumentException} with the
     * provided message.
     *
     * @param argument - the argument to check.
     * @param message  - the message to display.
     *
     * @see Objects#nonNull(Object)
     * @see Preconditions#checkArgument(boolean, Object)
     */
    public static void checkArgumentNonNull(Object argument, String message) {
        checkArgument(nonNull(argument), message);
    }

    /**
     * @param argument - the argument to check.
     *
     * @see Objects#nonNull(Object)
     * @see Preconditions#checkArgument(boolean)
     */
    public static void checkArgumentNonNull(Object argument) {
        checkArgument(nonNull(argument));
    }

    /**
     * Checks whether the {@link String} provided is null, empty, or contains only whitespaces, and throws an {@link
     * IllegalArgumentException} with the provided message, if that is the case.
     *
     * @param argument - the argument to check.
     * @param message  - the message to display.
     *
     * @throws IllegalArgumentException when the argument is blank.
     * @see com.google.common.base.Strings#nullToEmpty(String)
     * @see String#trim()
     * @see String#isEmpty()
     */
    public static void checkArgumentNotBlank(String argument, String message) {
        checkArgument(!nullToEmpty(argument).trim().isEmpty(), message);
    }
}
