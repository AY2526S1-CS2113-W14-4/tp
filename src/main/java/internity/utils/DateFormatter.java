package internity.utils;

import internity.core.Date;
import internity.core.InternityException;

/**
 * Utility class for parsing and validating date strings into {@link Date} objects.
 * <p>
 *     This class provides methods to convert string representations of the date in a strict
 *     {@code dd-MM-yyyy} format into a {@link Date} object. It also validates the input to ensure that
 *     the provided date values correspond to actual valid calendar dates, including leap year cases.
 * </p>
 * <p>
 *     This class cannot be instantiated.
 * </p>
 */
public class DateFormatter {
    /**
     * Private constructor to prevent instantiation.
     */
    private DateFormatter() {}

    /**
     * Parses a string representation of a date in the format {@code dd-MM-yyyy} into a
     * {@link Date} object.
     *
     * <p>Valid date format conditions:</p>
     * <ul>
     *     <li>The input must not be {@code null} or blank.</li>
     *     <li>The input must be in a {@code dd-MM-yyyy} format:
     *     <ul>
     *         <li>{@code dd} is a two-digit day (01-31)</li>
     *         <li>{@code MM} is a two-digit month (01-12)</li>
     *         <li>{@code yyyy} is a four-digit year</li>
     *     </ul>
     *     </li>
     *     <li>
     *         The date must represent a valid calendar date (e.g. 09-10-2025 is valid,
     *         99-99-2025 is invalid).
     *     </li>
     * </ul>
     *
     * @param dateString the date string to parse, expected in {@code dd-MM-yyyy} format
     * @return a {@link Date} object representing the parsed date
     * @throws InternityException if the input is {@code null}, blank, has an invalid format or invalid date
     */
    public static Date parse(String dateString) throws InternityException {
        if (dateString == null || dateString.isBlank()) {
            throw InternityException.invalidInput();
        }

        String trimmed = dateString.trim();

        // Check for an absolute dd-MM-yyyy format
        if (!trimmed.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw InternityException.invalidDateFormat();
        }

        String[] parts = trimmed.split("-");

        if (parts.length != 3) {
            throw InternityException.invalidDateFormat();
        }

        // Regex validation above guarantees numeric format, so parseInt() cannot throw NumberFormatException
        assert parts[0].matches("\\d+") && parts[1].matches("\\d+") && parts[2].matches("\\d+")
                : "Regex should guarantee numeric format";

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if (!isValidDate(day, month, year)) {
            throw InternityException.invalidDateFormat();
        }

        return new Date(day, month, year);
    }

    /**
     * Validates whether the provided day, month and year corresponds to a valid calendar date.
     *
     * @param day day component of the date
     * @param month month component of the date
     * @param year year component of the date
     * @return {@code true} if the date is valid, {@code false} otherwise
     */
    private static boolean isValidDate(int day, int month, int year) {
        if (day < 1 || month < 1 || month > 12) {
            return false;
        }

        int maxDay = switch (month) {
        case 2 -> (isLeapYear(year)) ? 29 : 28;
        case 4, 6, 9, 11 -> 30;
        default -> 31;
        };
        return day <= maxDay;
    }

    /**
     * Determines whether the provided year is a leap year according to the Gregorian calendar rules.
     *
     * <p>
     *     A year is a leap year if it is divisible by 4 but not 100, unless it is also divisible by 400.
     * </p>
     * @param year the year to check
     * @return {@code true} if the year is a leap year, {@code false} otherwise
     */
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
    }
}
